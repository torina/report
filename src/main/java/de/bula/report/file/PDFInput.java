package de.bula.report.file;

import org.springframework.stereotype.Component;
import technology.tabula.CommandLineApp;

import java.security.Permission;

@Component
public class PDFInput implements FileInput {
    @Override
    public void readFile(String fileLocation) {

        System.out.println("Preventing System.exit");
        SystemExitControl.forbidSystemExitCall();

        try {
            System.out.println("Running a program which calls System.exit");

            String[] args = ArgsUtil.buildArgs(fileLocation);

            CommandLineApp.main(args);

        } catch (SystemExitControl.ExitTrappedException e) {
            System.out.println("Forbidding call to System.exit");
        }


    }

    private static class SystemExitControl {
        public static void forbidSystemExitCall() {
            final SecurityManager securityManager = new SecurityManager() {
                @Override
                public void checkPermission(Permission permission) {
                    if (permission.getName().contains("exitVM")) {
                        throw new ExitTrappedException();
                    }
                }
            };
            System.setSecurityManager(securityManager);
        }

        public static void enableSystemExitCall() {
            System.setSecurityManager(null);
        }

        public static class ExitTrappedException extends SecurityException {
        }
    }
}
