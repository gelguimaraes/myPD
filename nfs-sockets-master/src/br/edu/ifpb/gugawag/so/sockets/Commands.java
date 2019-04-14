package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.lang.String;

public class Commands {

    private synchronized static String execCommand(final String commandLine) throws IOException {

        boolean success = false;
        String result;

        Process p;
        BufferedReader input;
        StringBuffer cmdOut = new StringBuffer();
        String lineOut = "";
        int numberOfOutline = 0;

        try {
            p = Runtime.getRuntime().exec(commandLine);

            input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((lineOut = input.readLine()) != null) {
                if (numberOfOutline > 0) {
                    cmdOut.append("\n");
                }
                cmdOut.append(lineOut);
                numberOfOutline++;
            }

            result = cmdOut.toString();
            success = true;
            input.close();

        } catch (IOException e) {
            result = String.format("Falha ao executar comando %s. Erro: %s", commandLine, e.toString());
        }

        if (!success) {
            throw new IOException(result);
        }
        return result;
    }

    public String setCmd(String comando, String dir, String file1, String file2) {
        String[] cmds = {
                "dir c:\\" + dir + "/b", // lista diretórios
                "cd c:\\" + dir + " && ren " + file1 + " " + file2, //renomeia arquivo em um diretoris
                "md c:\\" + dir, //cria diretorio
                "cd c:\\" + dir + " && type nul > " + file1, //cria arquivo em um diretorio
                "rd c:\\" + dir + " /s /q", //deleta diretorio
                "del c:\\" + dir + "\\" + file1 + " /s /q" //deleta arquivo
        };
        String cmd = "", resp = "";
        switch (comando) {
            case "readdir":
                cmd = cmds[0];
                resp = "Listando diretórios:\n";
                break;
            case "renamefile":
                cmd = cmds[1];
                resp = "Arquivo renomeado!\n";
                break;
            case "createdir":
                cmd = cmds[2];
                resp = "Diretório criado!\n";
                break;
            case "createfile":
                cmd = cmds[3];
                resp = "Arquivo criado!\n";
                break;
            case "removedir":
                cmd = cmds[4];
                resp = "Diretório removido!\n";
                break;
            case "removefile":
                cmd = cmds[5];
                resp = "";
                break;
        }

        String startCmd = "cmd /c";
        String result = null;
        try {
            result = execCommand(startCmd + cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(result);
        return resp + result;
    }
}