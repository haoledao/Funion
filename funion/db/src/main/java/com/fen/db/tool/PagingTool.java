package com.fen.db.tool;

import java.util.ArrayList;

/**
 * 分页查询
 * @author Fei
 */
public class PagingTool<T> extends ArrayList<T>{

	private static final long serialVersionUID = 5526933443772285251L;
	
	private int mTotalSize;

	public int size() {
		return super.size();
	}

	/**
	 * @return 总页数
	 */
	public int getTotalSize(){
		return mTotalSize;
	}

	public void setTotalSize(int totalSize){
		this.mTotalSize = totalSize;
	}

}