package tree;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;


final class TreeFixtures {

    private TreeFixtures() {
    }

    static void createFlatFiles(Path dir, int fileCount) throws IOException {
        for (int i = 0; i < fileCount; i++) {
            Files.createFile(dir.resolve("f" + i));
        }
    }


    static void createFlatDirectories(Path dir, int dirCount) throws IOException {
        for (int i = 0; i < dirCount; i++) {
            Files.createDirectory(dir.resolve("d" + i));
        }
    }


    static void createDeepChain(Path dir, int depth) throws IOException {
        Path current = dir;
        for (int i = 0; i < depth; i++) {
            Files.createFile(current.resolve("f"));
            current = Files.createDirectory(current.resolve("d"));
        }
    }

    static void createBalanced(Path dir, int branching, int depth, int filesPerDir) throws IOException {
        for (int i = 0; i < filesPerDir; i++) {
            Files.createFile(dir.resolve("f" + i));
        }
        if (depth > 0) {
            for (int b = 0; b < branching; b++) {
                Path child = Files.createDirectory(dir.resolve("d" + b));
                createBalanced(child, branching, depth - 1, filesPerDir);
            }
        }
    }


    static void createHiddenFiles(Path dir, int hiddenCount) throws IOException {
        for (int i = 0; i < hiddenCount; i++) {
            Files.createFile(dir.resolve(".h" + i));
        }
        Files.createFile(dir.resolve("visible"));
    }

    static void deleteRecursively(Path root) throws IOException {
        if (root == null || !Files.exists(root)) {
            return;
        }
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
