import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @ClassName: sudoku
* @Description: ����������ͬ�ֵ����
* @author WangKeXin
* @date 2019��12��23�� ����2:00:15
*
*/

public class sudoku {
	public static int count; //��Ҫ��������ĸ���
	public int curr;// ��ǰ����Ĳ���λ��
    public static final int side=9;
    public static final int SideSub = 3;
    public static final int init_num=6;

    public boolean init_state=true;  //�Ƿ��������һ��״̬  
    public byte[][] layout = null;// ����
    public byte[] ansFlag = null; //ÿ������λ�ý�ռ�ʹ�ñ�ʶ��ָ����һ��Ҫ����Ľ⣩
    							  //������Ժ��Ϊ1
    public byte[][] ans = null; //��¼ÿ��λ�õĽ�ռ�
    public Random random = new Random();
    
		public static void main(String[] args) {
			
			 if (args.length != 2) {  
		        System.out.println("�������벻��ȷ\n"); 
		        System.out.println("��ȷ�������ʽΪ��java sudoku -c ����������");
		        return;  
		    }else {
		    	if(!args[0].equals("-c")){
		    		System.out.println("�������벻��ȷ\n"); 
		            System.out.println("��ȷ�������ʽΪ��java sudoku -c ����������");
		            return; 
		    	}
		    	if(!isNumeric(args[1])){
		    		System.out.println("�������벻��ȷ\n"); 
		            System.out.println("��ȷ�������ʽΪ��java sudoku -c ����������");
		            return; 
		    	}
		    }
		    System.out.println("������main����ʱָ���Ĳ���������");  
		    for (int i = 0; i < args.length; i++) {  
		        System.out.println("����" + (i + 1) + "��ֵΪ��" + args[i]);  
		    } 
		    
		    count=Integer.valueOf(args[1]);
			sudoku sdk = new sudoku();
			//count=2;
			sdk.generateRandom(count);
		    
		}
		
		/**
		* @Title: isNumeric
		* @Description: �ж������Ƿ�Ϊ����
		* @param @param str
		* @param @return    
		* @return boolean    
		* @throws
		*/
		public static boolean isNumeric(String str) {
		    // ��������ʽ����ƥ�����е����� ��������
		    Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
		    String bigStr;
		    try {
		        bigStr = new BigDecimal(str).toString();
		    } catch (Exception e) {
		        return false;//�쳣 ˵�����������֡�
		    }

		    Matcher isNum = pattern.matcher(bigStr); // matcher��ȫƥ��
		    if (!isNum.matches()) {
		        return false;
		    }
		    return true;
		}
		
	    /**
	    * @Title: generateRandom
	    * @Description: ����Ҫ�������ĸ���ȫ����ʼ��
	    * @param @param count    
	    * @return void   
	    * @throws
	    */
	    public void generateRandom(int count) {
	        for (int i = 0; i < count; i++) {
	            init();
	            generate();
	        }
	    }	   
	    
		/**
		* @Title: init
		* @Description: ��ʼ������
		* @param    
		* @return void    
		* @throws
		*/
		public void init( )
		{

			if(layout==null)
				layout=new byte[side][side];
	        if (ansFlag == null)
	            ansFlag = new byte[side*side];     
	        if (ans == null)
	            ans = new byte[side*side ][side];
	        
	        for (int i = 0; i < side; i++) {
	        	for(int j=0;j<side;j++)
	        		layout[i][j] = -1;// ������ȫ������Ϊδ��״̬	          
	            ansFlag[i] = 0;// ������¼���λ�ã�����ʱ�����λ��������	               
	        }	        
	        for(int i=0;i<side*side;i++)
	        	for(int j=0;j<side;j++)
	        		ans[i][j] = -1;// ��ʼ��Ϊ�޽⣬���������ж�̬��ȡ
		}
		
	    /**
	    * @Title: WriteToFile
	    * @Description: ����ǰ����д���ļ�
	    * @param @param fw    
	    * @return void    
	    * @throws
	    */
	    public void WriteToFile(FileWriter fw) {
	        try {
	            for (int i = 0; i < side ; i++) {
	            	for(int j=0;j<side;j++){
	            		fw.write(String.valueOf(layout[i][j]+1));
	            		fw.write(" ");
	            	}
	                    fw.write("\n");
	            }
	            fw.write("\n");
	        } catch (Exception e) {
	        }
	    }

	    /**
	    * @Title: RandomAnswer
	    * @Description: �����������
	    * @param @param curr    
	    * @return void    
	    * @throws
	    */
	    private void RandomAnswer(int curr) {
	        // �������һ��˳��
	    	List<Byte> list = new LinkedList<Byte>();
	        for (int i = 0; i < side; i++)
	            list.add(ans[curr][i]);
	        int posi = 0, index = 0;
	        if(curr==0)
	        {
	        	ans[0][0]=init_num;
	        	list.remove(init_num);
	            index++;
	        }
	        while (list.size() != 0) {
	        	//posi����list�е�λ��
	            posi = Math.abs(random.nextInt()) % list.size();
	            ans[curr][index] = list.get(posi);
	            list.remove(posi);
	            index++;
	        }
	        list = null;
	    }
	    
	    /**
	    * @Title: getAnswerCount
	    * @Description: ��ý������
	    * @param @param curr
	    * @param @return    
	    * @return int    
	    * @throws
	    */
	    private int getAnswerCount(int curr) {
	        // ������ý������
	        int count = 0;
	        for (int i = 0; i < side; i++)
	            if (ans[curr][i] != -1)
	                count++;
	        return count;
	    }
	    
	    /**
	    * @Title: openFileWriter
	    * @Description: ���һ��д�ļ���������������֣����캯���ĵڶ�������true��ʾ׷��
	    * @param @param name
	    * @param @return    
	    * @return FileWriter    
	    * @throws
	    */
	    private FileWriter openFileWriter(String name) {
	        try {
	        	if(init_state) {
	        		init_state=false;
	        		return new FileWriter(name);
	        	}
	        	return new FileWriter(name,true);
	            
	        } catch (Exception e) {
	        }
	        return null;
	    }
	    
	    
	    /**
	    * @Title: getAnswer
	    * @Description: ����ֵָ��λ�õĿ��ý�
	    * @param @param curr    
	    * @return void    
	    * @throws
	    */
	    private void getAnswer(int curr) {
	        for (byte i = 0; i < side; i++)
	            ans[curr][i] =i; //�ٶ��������н�
	        //�����п��ܵĽ��г�ȥ���л�����Ѿ��е�
	        int x = curr / side, y = curr % side; //x�ǵ�ǰ�У�y�ǵ�ǰ��
	        for (int i = 0; i < side; i++) {
	            if (layout[i][y] != -1) //������������
	            {
	            	ans[curr][layout[i][y]] = -1; //ɾȥ�������
	            }	                
	            if (layout[x][i] != -1)  //������������
	            {
	            	ans[curr][layout[x][i]] = -1;
	            }         
	        }	        
	        //����3X3������Ҳ������ͬ
	        int x2 = x / SideSub, y2 = y / SideSub;  //��λ����3X3�����λ��
	        for (int i = x2 * SideSub; i < SideSub + x2 * SideSub; i++) {
	            for (int j = y2 * SideSub; j < SideSub + y2 * SideSub; j++) {	        
	                if (layout[i][j] != -1)
	                    ans[curr][layout[i][j]] = -1; //ɾȥ��3X3�г��ֹ�������
	            }
	        }
	        RandomAnswer(curr);
	    }
	    
	    /**
	    * @Title: getAnswerNum
	    * @Description: �õ���ǰλ�ÿ��ܽ�ĸ���
	    * @param @param curr
	    * @param @param state
	    * @param @return    
	    * @return byte    
	    * @throws
	    */
	    private byte getAnswerNum(int curr, int state) {
	        int cnt = 0;
	        for (int i = 0; i < side; i++) {
	            // �ҵ�ָ��λ�õĽ⣬����
	            if (cnt == state && ans[curr][i] != -1)
	            	return ans[curr][i];
	            if (ans[curr][i] != -1)
	                cnt++;// �ǽ⣬����������
	        }
	        return -1;// û���ҵ����߼�û������Ļ���Ӧ�ò������������
	    }
	   
	    /**
	    * @Title: generate
	    * @Description: ����һ�ֲ���
	    * @param @param layoutCount ���ɵĲ�����
	    * @param @return    
	    * @return long  
	    * @throws
	    */
	    public long generate() {
	    	FileWriter out = openFileWriter("layout.txt");
	        curr = 0;   //��ǰ����Ĳ���λ��	       
	        Boolean flag=true;
	        while (flag) {
	            if (ansFlag[curr] == 0)  
	                getAnswer(curr); //������λ��û�б����ݹ����Ͳ������¼����ռ�
	            int ansCount = getAnswerCount(curr);
	            if (ansCount == ansFlag[curr] && curr == 0) // ȫ���������
	                break;
	            if (ansCount == 0) {
	                ansFlag[curr] = 0;// �޿��ý⣬Ӧ�þ���0
	                curr--;
	                layout[curr/side][curr%side] = -1;
	                continue;
	            }
	            // ���ý�����
	            else if (ansFlag[curr] == ansCount) {
	                ansFlag[curr] = 0;
	                curr--;
	                layout[curr/side][curr%side] = -1;
	                continue;
	            } else {
	                // ����ָ������У��ڼ�����
	            	layout[curr/side][curr%side]= getAnswerNum(curr, ansFlag[curr]);
	                ansFlag[curr++]++;
	            }
	            if (side * side == curr) {
	                if (out != null)
	                	WriteToFile(out);
	                flag=false;
	                curr--;
	                layout[curr/side][curr%side] = -1; //���λ�����
	                ansFlag[curr] = 1;// ��λ�ñ�ʶ����,��Ϊ��ʹ��������
	            }
	        }
	        try {
	            out.close();
	        } catch (Exception e) {
	        }
	        System.out.println("������ϣ������ɣ�" + count);
	        return count;
	    }
}

