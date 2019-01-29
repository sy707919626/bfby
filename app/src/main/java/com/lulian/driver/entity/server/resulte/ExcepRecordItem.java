package com.lulian.driver.entity.server.resulte;


import java.io.Serializable;
import java.util.List;

/**
 * 异常记录对象
 * @author hxb
 */
public class ExcepRecordItem implements Serializable {

    private String tpid;
    private String ProblemDiscript;
    private List<File> Files;

    public String getTpid() {
        return tpid;
    }

    public void setTpid(String tpid) {
        this.tpid = tpid;
    }

    public String getProblemDiscript() {
        return ProblemDiscript;
    }

    public void setProblemDiscript(String problemDiscript) {
        ProblemDiscript = problemDiscript;
    }

    public List<File> getFiles() {
        return Files;
    }

    public void setFiles(List<File> files) {
        Files = files;
    }

    public static class File implements Serializable{
        private String FileId;
        private String FileName;
        private String Url;

        public String getFileId() {
            return FileId;
        }

        public void setFileId(String fileId) {
            FileId = fileId;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }
    }

}
