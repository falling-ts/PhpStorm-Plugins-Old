package com.yuanshang.PhpStorm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.AssertionError;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.actionSystem.CommonDataKeys;

public class ImportFormat extends AnAction {

    private Project project;

    @Override
    public void actionPerformed(AnActionEvent event) {
        this.project = event.getProject();
        try {
            this.execAction(event);
        } catch (AssertionError error) {
            Messages.showMessageDialog(
                    this.project,
                    "未打开代码文件或鼠标焦点未在代码文件上",
                    "错误提示",
                    Messages.getInformationIcon());
            return;
        }
    }

    private void execAction(AnActionEvent event) {

        // 获取当前活动代码
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        String code = document.getText();

        // 定义正则匹配规则
        String pattern = "^((import\\b.*?\\n)+)([\\s\\S]*)$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(code);
        if (!m.find()) {
            Messages.showMessageDialog(this.project,
                    "import 必须在最顶上，不可以有缩进；或者没有 import 导入",
                    "错误提示",
                    Messages.getInformationIcon());
            return;
        }

        // 捕获数据
        String importRaw = m.group(1);
        final String foot = m.group(3).trim();

        // 冒泡排序
        String[] imports = importRaw.split("\\n");
        for (int i = imports.length; i > 0; i--) {
            for (int j = 0; j < i; j++ ) {
                if (j == 0) {
                    continue;
                }
                if (imports[j-1].length() > imports[j].length()) {
                    String temp = imports[j];
                    imports[j] = imports[j-1];
                    imports[j-1] = temp;
                }
            }
        }

        // 拼接字符串
        StringBuilder importString = new StringBuilder();
        for (int i = 0; i < imports.length; i++) {
            importString.append(imports[i] + "\n");
        }
        importString.append("\n");

        // 写入操作
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.setText(importString.toString() + foot + "\n");
            }
        };
        WriteCommandAction.runWriteCommandAction(this.project, runnable);
    }
}
