package UI.Table;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author fanhoujun
 *
 */
public class TableContent
{
	private static final int MAX_COLUMN=128;	// The maximum column number
	private List<CellData> list=new ArrayList<CellData>();
	private int rowCount=0;
	private boolean isNewLine=true;

	public void println()
	{
		if(isNewLine)
		{
			return;
		}
		list.add(null);
		rowCount++;
		isNewLine=true;
	}
	
	public void append(Object value){
		append(value,1,1);
	}

	public void append(Object value,int colSpan){
		append(value,colSpan,1);
	}
	
	public void append(Object value,int colSpan,int rowSpan)
	{
		if(colSpan<1||rowSpan<1)
		{
			throw new IllegalArgumentException("Both colSpan and rowSpan must bigger than (colSpan:+"+colSpan+",rowSpan:"+rowSpan+")");
		}
		if(isNewLine)
		{
			isNewLine=false;		
		}
		list.add(new CellData(value,colSpan,rowSpan));
	}
		
	public GSTable createTable(){
		return createTable(null);
	}
	@SuppressWarnings("unchecked")

	public GSTable createTable(Object[]columnHeaders)
	{
		println();		
		List<CellData>[] listData=new List[rowCount];
		for (int i = 0; i < listData.length; i++)
		{
			listData[i]=new ArrayList<CellData>();
		}		
		CellData[][] cover=new CellData[rowCount][100];//record covered cell
		int cusorRow=0;
		int cusorCol=0;
		int column=0;
		for (CellData cellData : list)
		{
			if(cellData!=null)
			{
				if(cusorCol==MAX_COLUMN)
				{
					throw new RuntimeException("column number should not bigger than "+MAX_COLUMN);
				}
				while(cover[cusorRow][cusorCol]!=null)
				{
					cusorCol++;
					listData[cusorRow].add(null);
				}
				listData[cusorRow].add(cellData);				
				setSpanHidden(cusorRow, cusorCol, cellData, cover);
				while(cover[cusorRow][++cusorCol]!=null)
				{
					listData[cusorRow].add(null);
				}
			}
			else
			{
				column=column<cusorCol?cusorCol:column;
				cusorCol=0;
				cusorRow++;
			}
		}
		if(columnHeaders==null)
		{
			columnHeaders=new Object[column];
		}
		return new GSTable(new GridSplitImpl(listData,cover),createTableModel(listData,columnHeaders));
	}
	
	private DefaultTableModel createTableModel(List<CellData>[] listData,Object[] columnHeaders)
	{
		Object[][]data=new Object[listData.length][columnHeaders.length];
		int row=0;
		for(List<CellData> rowList:listData)
		{
			int col=0;
			for(CellData cellData:rowList)
			{
				data[row][col++]=cellData==null?null:cellData.getValue();				         
			}
			row++;
		}
		return new GSTableModel(data,columnHeaders);
	}
	
	/**
	 * set the covered cell of the cell at hidden status
	 * @param row
	 * @param col
	 */
	private void setSpanHidden(int row,int col,CellData cellData,CellData[][] cover)
	{
		if(cellData.getColSpan()<2&&cellData.getRowSpan()<2)
		{
			return;
		}
		CellData coverData=CellData.creatHiddenCell(row, col);
		if(col+cellData.getColSpan()>=MAX_COLUMN)
		{
			throw new RuntimeException("column number should not bigger than "+MAX_COLUMN);
		}
		
		for(int i=0;i<cellData.getColSpan();i++)
		{
			for(int j=0;j<cellData.getRowSpan();j++)
			{
				if(row+j<cover.length)
				{
					cover[row+j][col+i]=coverData;
				}else{
					cellData.setRowSpan(j);					
				}
			}
		}
		cover[row][col]=null;
	}
	
}
