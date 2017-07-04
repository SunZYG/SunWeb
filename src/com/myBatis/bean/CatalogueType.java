package com.myBatis.bean;

import org.springframework.stereotype.Component;

@Component("catalogueType")  
public class CatalogueType {
    private Integer id;

    private String type;

    private String cn;

    private String uuid;
    
    private String author;
    
    private String kind;
    
    private String createDate;
    
    private String updateDate;
    
    private int urlIndex;

    public Integer getId() {
        return id;
    }
    public CatalogueType(){
    	super();
    };
    public CatalogueType( String type, String cn, String uuid, String author, 
    		String kind, String createDate,String updateDate, int urlIndex) {
		super();
//		this.id = id;
		this.type = type;
		this.cn = cn;
		this.uuid = uuid;
		this.author = author;
		this.kind = kind;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.urlIndex = urlIndex;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getUrlIndex() {
		return urlIndex;
	}

	public void setUrlIndex(int urlIndex) {
		this.urlIndex = urlIndex;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}