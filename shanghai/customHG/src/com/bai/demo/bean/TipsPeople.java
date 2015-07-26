package com.bai.demo.bean;

import java.io.Serializable;

/**
 * 工作量统计--个人查验单证-查询本人处理单证情况
 * @author zhangyx
 *
 */
public class TipsPeople implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Integer NotInput;//已派单未填写查验结果数量
    private Integer HaveInput;//已填写查验结果数量
    private Integer WaitAll;//共移交单证数量
    private Integer HaveHandle;//已处理完毕单证数量
    private Integer NoHandle;//尚未处理单证数量
	public Integer getNotInput() {
		return NotInput;
	}
	public void setNotInput(Integer notInput) {
		NotInput = notInput;
	}
	public Integer getHaveInput() {
		return HaveInput;
	}
	public void setHaveInput(Integer haveInput) {
		HaveInput = haveInput;
	}
	public Integer getWaitAll() {
		return WaitAll;
	}
	public void setWaitAll(Integer waitAll) {
		WaitAll = waitAll;
	}
	public Integer getHaveHandle() {
		return HaveHandle;
	}
	public void setHaveHandle(Integer haveHandle) {
		HaveHandle = haveHandle;
	}
	public Integer getNoHandle() {
		return NoHandle;
	}
	public void setNoHandle(Integer noHandle) {
		NoHandle = noHandle;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
