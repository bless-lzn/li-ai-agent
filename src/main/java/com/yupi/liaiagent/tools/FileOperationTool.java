package com.yupi.liaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class FileOperationTool {
    private final String FILE_DIR=FileConstant.FILE_SAVE_DIR+"/file";

    //文件读取工具
    @Tool(description = "Read content from a file ")
    public String readFile(@ToolParam(description = "Name of the file to read") String fileName ) {
        String filePath = FILE_DIR+"/"+fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        }catch (Exception e){
            return "文件读取失败:"+e.getMessage();
        }
    }

    //文件写入工具
    @Tool(description = "Write content to a file ")
    public String writeFile(@ToolParam(description = "Name of the file to write") String fileName,@ToolParam(description = "Content of the file to write")String content){

        try {
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content,FILE_DIR+"/"+fileName);
            return "The file was written successfully:";
        } catch (IORuntimeException e) {
            return "File write failed:"+e.getMessage();
        }
    }
}
