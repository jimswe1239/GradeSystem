package UI.Table;

interface GridSplit {
	/** 
	 * @param row£ºlogic row of specified cell
	 * @param column£ºlogic column of specified cell
	 * @return covered column of specified cell
	 */
	int spanCol(int row, int column);

	/** 
	 * @param row£ºlogic row of specified cell
	 * @param column£ºlogic column of specified cell
	 * @return the column number of visible header cell which covers the specified column. If the specified header cell itself is visible, return its own column number
	 */
	int visibleColCell(int row, int column);
	
	/** 
	 * @param row£ºlogic row of specified cell
	 * @param column£ºlogic column of specified cell
	 * @return the covered row number of the specified cell
	 */
	int spanRow(int row, int column);

	/** 
	 * @param row£ºlogic row of specified cell
	 * @param column£ºlogic column of specified cell
	 * the row number of visible header cell which covers the specified row. If the specified header cell itself is visible, return its own row number
	 */
	int visibleRowCell(int row, int column);
	
	boolean isVisible(int row, int column);
}
