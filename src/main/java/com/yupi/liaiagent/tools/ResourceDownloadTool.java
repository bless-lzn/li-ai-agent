package com.yupi.liaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class ResourceDownloadTool {
    @Tool(description = "Download a resource from a URL")
    public String downloadResource(@ToolParam(description = "URL of the resource to download") String url, @ToolParam(description = "Name of the file to save") String filename){
        String fileDir = FileConstant.FILE_SAVE_DIR+"/download";
        String filePath=fileDir+"/"+filename;
        try {
            HttpUtil.downloadFile(url,filePath);
            return "Download successful. File saved at: " + filePath;
        } catch (Exception e) {
            return "Error downloading resource: " + e.getMessage();

        }



    }
}
