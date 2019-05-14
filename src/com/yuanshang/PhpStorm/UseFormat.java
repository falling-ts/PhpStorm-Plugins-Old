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

public class UseFormat extends AnAction {
    
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
        String pattern = "(<\\?php[\\s\\S]*?\\n)(use\\b.*?;([\\s\\S]*?\\nuse\\b.*?;)+)([\\s\\S]*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(code);
        if (!m.find()) {
            Messages.showMessageDialog(this.project,
                "无 use 导入操作",
                "错误提示",
                Messages.getInformationIcon());
            return;
        }
    
        // 捕获数据
        String headRaw = m.group(1);
        String useBodyRaw = m.group(2);
        final String foot = m.group(4).trim();
    
        // 头部 `// 注释` 删除
        Pattern headR = Pattern.compile("//.*?\\n");
        Matcher headM = headR.matcher(headRaw);
        final String head = headM.replaceAll("").trim();
    
        // use 导入处理
        Pattern bodyR = Pattern.compile(";[\\s\\S]*?\\nuse\\b");
        Matcher bodyM = bodyR.matcher(useBodyRaw);
        final String useBody = bodyM.replaceAll(";\nuse").trim();
        
        // 冒泡排序
        String[] uses = useBody.split("\\n");
        for (int i = uses.length; i > 0; i--) {
            for (int j = 0; j < i; j++ ) {
                if (j == 0) {
                    continue;
                }
                if (uses[j-1].length() > uses[j].length()) {
                    String temp = uses[j];
                    uses[j] = uses[j-1];
                    uses[j-1] = temp;
                }
            }
        }
    
        // 拼接字符串
        StringBuilder use = new StringBuilder("\n\n");
        for (int i = 0; i < uses.length; i++) {
            use.append(uses[i] + "\n");
        }
        use.append("\n");
        
        // 写入操作
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.setText(head + use.toString() + foot + "\n");
            }
        };
        WriteCommandAction.runWriteCommandAction(this.project, runnable);
    }
    
}
