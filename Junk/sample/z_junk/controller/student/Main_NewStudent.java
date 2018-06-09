package sample.z_junk.controller.student;

public class Main_NewStudent {
    private static Main_NewStudent ourInstance;

    private NewStudent_controller newStudent;

    private MorePersonalDetail_controller morePersonalDetail;

    private ActivityForStudent_controller activityForStudent;



    private Main_NewStudent() {
    }

    public static Main_NewStudent getInstance() {
        if(ourInstance == null){
            ourInstance = new Main_NewStudent();
        }
        return ourInstance;
    }

    public NewStudent_controller getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(NewStudent_controller newStudent) {
        this.newStudent = newStudent;
    }

    public MorePersonalDetail_controller getMorePersonalDetail() {
        return morePersonalDetail;
    }

    public void setMorePersonalDetail(MorePersonalDetail_controller morePersonalDetail) {
        this.morePersonalDetail = morePersonalDetail;
    }

    public ActivityForStudent_controller getActivityForStudent() {
        return activityForStudent;
    }

    public void setActivityForStudent(ActivityForStudent_controller activityForStudent) {
        this.activityForStudent = activityForStudent;
    }
}
