package com.thelionking.campus;

/**
 * @author the lion king
 * Fragment的类
 */
public enum Type{
	//用户中心
	USER(0),
	//校园资讯
	
	CAMPUS_MESSAGE(1),
	//课程表
	 SYLLABUS(2),
    //自习室
    SELF_STUDY_ROOM(3),
    //考试题
//    QUESTION_SHARE(3),
    //美食
    DELICACY(4),
    //周边
//    SURRORDING(6),
    //设置
    SERVICE(5),
    SETTING(6);
	
	
	
    private int number;

    private Type(int number){
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }
}
