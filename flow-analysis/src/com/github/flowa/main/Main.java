package com.github.flowa.main;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.github.detentor.codex.monads.Option;
import com.github.flowa.analysis.ClassAnalysis;
import com.github.flowa.analysis.LibraryAnalysis;
import com.github.flowa.entities.FlowClass;
import com.github.flowa.entities.FlowLibrary;
import com.github.flowa.entities.FlowMethod;

public class Main
{
    public static void main(final String[] args)
    {
        final String ultimoArquivo = "D:\\apache-maven-3.0.5\\maven_repo\\com\\github\\detentor\\codexCollections\\0.0.21\\codexCollections-0.0.21.jar";
        final String penultimoArquivo = "D:\\apache-maven-3.0.5\\maven_repo\\com\\github\\detentor\\codexCollections\\0.0.20\\codexCollections-0.0.20.jar";
        
        // final String ultimoArquivo = "C:\\Users\\vinicius\\Downloads\\atc-modelos-1.0.1.jar";
        // final String penultimoArquivo = "C:\\Users\\vinicius\\Downloads\\atc-modelos-1.0.2-SNAPSHOT.jar";

        // final String ultimoArquivo = "C:\\Users\\vinicius\\Downloads\\gcs-servicotoken-1.0.0.jar";
        // final String penultimoArquivo = "C:\\Users\\vinicius\\Downloads\\gcs-servicotoken-1.0.0-SNAPSHOT.jar";

        System.out.println("starting...");

        final FlowLibrary ultimaVersao = loadData(ultimoArquivo);
        final FlowLibrary penultimaVersao = loadData(penultimoArquivo);

        // System.out.println(ultimaVersao);
        // System.out.println("\n\n\n");
        // System.out.println(penultimaVersao);

        System.out.println(analyse(penultimaVersao, ultimaVersao));

        // listar:
        // 1 - as novas classes
        // 2 - os novos metodos

        // importantes:

        // 1 - quais metodos mudaram o contrato (os parametros ou a saida)

        // final List<Class<?>> classes = loadClasses(arquivoAnalisar);
        // final Map<Class<?>, Map<Method, Class<?>>> analysis = analyseClass(classes);

    }

    private static LibraryAnalysis analyse(final FlowLibrary previous, final FlowLibrary current)
    {
        if (!previous.getName().equals(current.getName()))
        {
            System.out.println("As bibliotecas sao diferentes");
        }

        final LibraryAnalysis libraryAnalysis = new LibraryAnalysis(previous.getName(), previous.getVersion(), current.getVersion());

        for (final FlowClass prevClass : previous.getFlowClasses())
        {
            final int theIndex = current.getFlowClasses().indexOf(prevClass);

            final ClassAnalysis classAnalysis = new ClassAnalysis(prevClass.getName());

            if (theIndex > -1)
            {
                final FlowClass currentClass = current.getFlowClasses().get(theIndex);

                for (final FlowMethod flowMethod : prevClass.getMethods())
                {
                    // Se a classe atual possui o metodo, ele nao foi modificado
                    if (currentClass.getMethods().contains(flowMethod))
                    {
                        classAnalysis.getUnmodified().add(flowMethod);
                    }
                    else
                    {
                        // Se nao possui mais o metodo, ele foi removido
                        classAnalysis.getRemoved().add(flowMethod);
                    }
                }

                // no final, identifica os metodos da atual que nao existiam na anterior
                for (final FlowMethod curMethod : currentClass.getMethods())
                {
                    if (!classAnalysis.getUnmodified().contains(curMethod))
                    {
                        classAnalysis.getAdded().add(curMethod);
                    }
                }

                final Set<FlowMethod> firstSet = new HashSet<>(prevClass.getMethods());
                final Set<FlowMethod> secondSet = new HashSet<>(currentClass.getMethods());

                if (firstSet.equals(secondSet))
                {
                    libraryAnalysis.getUnmodified().add(classAnalysis);
                }
                else
                {
                    libraryAnalysis.getModified().add(classAnalysis);
                }
            }
            else
            {
                libraryAnalysis.getRemoved().add(classAnalysis);
            }
        }

        for (final FlowClass curClass : current.getFlowClasses())
        {
            if (!libraryAnalysis.hasClass(curClass.getName()))
            {
                // Se a classe nao existe, cria a analise com todas as informacoes
                final ClassAnalysis curAnalysis = new ClassAnalysis(curClass.getName());
                curAnalysis.getAdded().addAll(curClass.getMethods());
                libraryAnalysis.getAdded().add(curAnalysis);
            }
        }

        return libraryAnalysis;
    }

    /**
     * Load all classes from a jar file. <br/>
     * Nao carrega as classes internas e metodos nao publicos
     * 
     * @param filename
     * @return
     */
    public static FlowLibrary loadData(final String filename)
    {
        final FlowLibrary flowLibrary = new FlowLibrary("unversioned", "unamed");

        try (final JarFile jarFile = new JarFile(filename))
        {
            final Enumeration<JarEntry> e = jarFile.entries();

            final URL[] urls = { new URL("jar:file:" + filename + "!/") };
            final URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements())
            {
                final JarEntry je = e.nextElement();

                if (je.getName().contains("pom.properties"))
                {
                    final Properties prop = new Properties();
                    try (InputStream input = cl.getResourceAsStream(je.getName()))
                    {
                        prop.load(input);
                        flowLibrary.setName(prop.getProperty("groupId") + "/" + prop.getProperty("artifactId"));
                        flowLibrary.setVersion(prop.getProperty("version"));
                    }
                }
                else if (je.getName().endsWith(".class") && !je.getName().contains("$")) // descarta as classes internas
                {
                    final String className = je.getName().substring(0, je.getName().length() - 6).replace('/', '.');

                    // parte teste
                    try (InputStream iStream = cl.getResourceAsStream(je.getName()))
                    {
                        final byte[] data = new byte[iStream.available()];
                        iStream.read(data);

                        final ClassReader cr = new ClassReader(data);

                        final FlowClass flowClass = new FlowClass(className);

                        final ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM6)
                        {

                            @Override
                            public void visit(final int version, final int access, final String name, final String signature,
                                    final String superName,
                                    final String[] interfaces)
                            {

                                super.visit(version, access, name, signature, superName, interfaces);
                            }

                            @Override
                            public MethodVisitor visitMethod(final int access, final String name, final String descriptor,
                                    final String signature, final String[] exceptions)
                            {
                                if (!name.startsWith("<"))
                                {
                                    System.out.println("nome " + name + "/" + signature + "/" + descriptor);

                                    final boolean isPublic = (access & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC;
                                    flowClass.getMethods().add(new FlowMethod(name, descriptor, isPublic));
                                }

                                return super.visitMethod(access, name, descriptor, signature, exceptions);
                            }
                            
                            
                        };

                        cr.accept(classVisitor, 0);
                        flowLibrary.getFlowClasses().add(flowClass);
                    }
                    catch (final IllegalArgumentException iae)
                    {
                        iae.printStackTrace();
                    }
                    //

                    // final Class<?> curClass = cl.loadClass(className);
                    // final FlowClass flowClass = new FlowClass(curClass.getName());
                    //
                    // for (final Method curMethod : curClass.getDeclaredMethods())
                    // {
                    // final boolean isPublic = (curMethod.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC;
                    // final List<String> parameters = getParameters(curMethod);
                    //
                    // if (isPublic)
                    // {
                    // flowClass.getMethods()
                    // .add(new FlowMethod(curMethod.getName(), parameters, isPublic, curMethod.getReturnType().getName()));
                    // }
                    // }

                }
            }
        }
        catch (final Exception e)
        {
            throw new RuntimeException(e);
        }

        return flowLibrary;
    }

}
