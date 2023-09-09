package com.gzpclass.supdem.Controller;


import java.io.IOException;

public class TxTsp {
	private int cityNum; // 城市数量
	private int[][] distance; // 距离矩阵
	private int[] colable;//代表列，也表示是否走过，走过置0
	private int[] row;//代表行，走过置0
	public TxTsp(int n) {
		cityNum = n;
	}

	public void init(float[][] distanceMatrix) throws IOException {
		// 读取数据
		distance = new int[cityNum][cityNum];
		// 计算距离矩阵
		// 针对具体问题，距离计算方法也不一样
		for (int i = 0; i < cityNum - 1; i++) {
			distance[i][i] = (int)distanceMatrix[i][i];
			for (int j = i + 1; j < cityNum; j++) {
					distance[i][j] = (int)distanceMatrix[i][j];
					distance[j][i] =(int) distanceMatrix[j][i];
			}
		}

		distance[cityNum - 1][cityNum - 1] =(int)distanceMatrix[cityNum - 1][cityNum - 1] ;

		colable = new int[cityNum];
		colable[0] = 0;
		for (int i = 1; i < cityNum; i++) {
			colable[i] = 1;
		}

		row = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {
			row[i] = 1;
		}

	}

	public int[] solve(){

		int[] temp = new int[cityNum];
		String path="0";
		int[] output=new int[cityNum+1];
		output[0]=0;
		int number=0;

		int s=0;//计算距离
		int i=0;//当前节点
		int j=0;//下一个节点
		//默认从0开始
		while(row[i]==1){
			for (int k = 0; k < cityNum; k++) {
				temp[k] = distance[i][k];
				//System.out.print(temp[k]+" ");
			}
			//System.out.println();
			//选择下一个节点，要求不是已经走过，并且与i不同
			j = selectmin(temp);
			//找出下一节点
			row[i] = 0;//行置0，表示已经选过
			colable[j] = 0;//列0，表示已经走过
			number+=1;
			output[number]=j;
			path+="-->" + j;
			//System.out.println(i + "-->" + j);
			//System.out.println(distance[i][j]);
			s = s + distance[i][j];
			i = j;//当前节点指向下一节点
		}
		System.out.println("路径:" + path);
		System.out.println("总距离为:" + s);
		return output;

	}

	public int selectmin(int[] p){
		int j = 0, m = p[0], k = 0;
		//寻找第一个可用节点，注意最后一次寻找，没有可用节点
		while (colable[j] == 0) {
			j++;
			//System.out.print(j+" ");
			if(j>=cityNum){
				//没有可用节点，说明已结束，最后一次为 *-->0
				m = p[0];
				break;
				//或者直接return 0;
			}
			else{
				m = p[j];
			}
		}
		//从可用节点J开始往后扫描，找出距离最小节点
		for (; j < cityNum; j++) {
			if (colable[j] == 1) {
				if (m >= p[j]) {
					m = p[j];
					k = j;
				}
			}
		}
		return k;
	}


	public void printinit() {
		System.out.println("print begin....");
		for (int i = 0; i < cityNum; i++) {
			for (int j = 0; j < cityNum; j++) {
				System.out.print(distance[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("print end....");
	}
}