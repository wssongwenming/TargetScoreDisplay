package com.bcsb.rabbitmq.entity;

public enum Command {
	waiting("等候中",0),signin_by_pass("密码登陆",1),signin_by_face("人脸登陆",2),shoot("射击",3),finish("结束",4);
	private int index;
    private String value; 


    private Command(String value,int index) {  
        this.value = value;  
        this.index=index;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	
    
}
