package com.myBatis.bean;

import org.springframework.stereotype.Component;

@Component("catalogue")  
public class Catalogue {
    public Catalogue(String href, String content) {
		super();
		this.href = href;
		this.content = content;
	}
    
    public Catalogue(String indexName, String href, String type, String index, String name, String intIndex) {
		super();
		this.indexName = indexName;
		this.href = href;
		this.type = type;
		this.index = index;
		this.name = name;
		this.intIndex = intIndex;
	}

	public Catalogue() {
		super();
	}

	private Integer id;

    private String indexName;

    private String href;

    private String type;

    private String content;
    
    private String index;
    
    private String name;
    
    public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(String intIndex) {
		this.intIndex = intIndex;
	}

	private String intIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}