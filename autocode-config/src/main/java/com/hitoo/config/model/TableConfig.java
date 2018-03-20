package com.hitoo.config.model;

public class TableConfig {
	private String name ;
	
	private boolean select;
	
	/** The insert statement enabled. */
    private boolean insertStatementEnabled;

    /** The select by primary key statement enabled. */
    private boolean selectByPrimaryKeyStatementEnabled;

    /** The select by example statement enabled. */
    private boolean selectByExampleStatementEnabled;

    /** The update by primary key statement enabled. */
    private boolean updateByPrimaryKeyStatementEnabled;

    /** The delete by primary key statement enabled. */
    private boolean deleteByPrimaryKeyStatementEnabled;

    /** The delete by example statement enabled. */
    private boolean deleteByExampleStatementEnabled;

    /** The count by example statement enabled. */
    private boolean countByExampleStatementEnabled;

    /** The update by example statement enabled. */
    private boolean updateByExampleStatementEnabled;
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean isInsertStatementEnabled() {
		return insertStatementEnabled;
	}

	public void setInsertStatementEnabled(boolean insertStatementEnabled) {
		this.insertStatementEnabled = insertStatementEnabled;
	}

	public boolean isSelectByPrimaryKeyStatementEnabled() {
		return selectByPrimaryKeyStatementEnabled;
	}

	public void setSelectByPrimaryKeyStatementEnabled(boolean selectByPrimaryKeyStatementEnabled) {
		this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
	}

	public boolean isSelectByExampleStatementEnabled() {
		return selectByExampleStatementEnabled;
	}

	public void setSelectByExampleStatementEnabled(boolean selectByExampleStatementEnabled) {
		this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
	}

	public boolean isUpdateByPrimaryKeyStatementEnabled() {
		return updateByPrimaryKeyStatementEnabled;
	}

	public void setUpdateByPrimaryKeyStatementEnabled(boolean updateByPrimaryKeyStatementEnabled) {
		this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
	}

	public boolean isDeleteByPrimaryKeyStatementEnabled() {
		return deleteByPrimaryKeyStatementEnabled;
	}

	public void setDeleteByPrimaryKeyStatementEnabled(boolean deleteByPrimaryKeyStatementEnabled) {
		this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
	}

	public boolean isDeleteByExampleStatementEnabled() {
		return deleteByExampleStatementEnabled;
	}

	public void setDeleteByExampleStatementEnabled(boolean deleteByExampleStatementEnabled) {
		this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
	}

	public boolean isCountByExampleStatementEnabled() {
		return countByExampleStatementEnabled;
	}

	public void setCountByExampleStatementEnabled(boolean countByExampleStatementEnabled) {
		this.countByExampleStatementEnabled = countByExampleStatementEnabled;
	}

	public boolean isUpdateByExampleStatementEnabled() {
		return updateByExampleStatementEnabled;
	}

	public void setUpdateByExampleStatementEnabled(boolean updateByExampleStatementEnabled) {
		this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
	}
    
}
