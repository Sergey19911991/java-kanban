
public class Task {
    private String nameTask;
    private String descriptionTask;
    private String statusTask;

    public Task(String nameTask, String descriptionTask) {
        this.nameTask=nameTask;
        this.descriptionTask=descriptionTask;
    }


    public String getNameTask(){
        return this.nameTask;
    }

    public String getDescriptionTask(){
        return this.descriptionTask;
    }

    public String getStatusTask() {
        return this.statusTask;
    }

    public void setStatusTask (String statusTask){
        this.statusTask = statusTask;
    }

}
