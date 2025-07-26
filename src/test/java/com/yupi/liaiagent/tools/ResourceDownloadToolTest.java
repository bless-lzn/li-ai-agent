package com.yupi.liaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceDownloadToolTest {
    @Test
    public void testDownload() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String result = resourceDownloadTool.downloadResource("https://vfiles.gtimg.cn/tvideo2/channel-vue/assets/logo-Ckf4UreJ.svg", "tcent.svg");
    }
}