package com.lessonForm.lesson;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Grade {
	 public List<Score> getScore(HttpServletRequest request,String fileName){
		 try {
				final String path=request.getSession().getServletContext()
						.getRealPath("/")
						+ "Lessons"; // 上传文件 存放目录;;;
				File file = new File(path+File.separator+fileName+"_score.html");
				@SuppressWarnings("resource")
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), "gb2312"));
				StringBuilder html = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					html.append(line);
					html.append('\n');
				}
				return getScores(html.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		 
	 }
	 public List<Score> getScores(String html) throws ParserException {
	        List<Score> list = new ArrayList<Score>();
	        Parser parser = new Parser(html);
	        parser.setEncoding("gb2312");
	        NodeFilter filter = new NodeFilter() {
	            public boolean accept(Node node) {
	                if (node.getText().startsWith("table class=\"table listTable\"")) {

	                    return true;
	                }
	                return false;
	            }
	        };
	        NodeList resultNodeList = parser.extractAllNodesThatMatch(filter);
	        Node tableNode = resultNodeList.elementAt(0);
	        NodeList childNodeList = tableNode.getChildren();
	        SimpleNodeIterator iter = childNodeList.elements();
	        while (iter.hasMoreNodes() && !(iter.nextNode() instanceof TableRow)) ;
	        while (iter.hasMoreNodes()) {
	            Node tempNode = iter.nextNode();
	            if (!(tempNode instanceof TableRow)) continue;

	            Score score = new Score();
	            NodeList tempChildNodeList = tempNode.getChildren();


	            SimpleNodeIterator tempIter = tempChildNodeList.elements();
	            for (int i = 0; i < 10; i++) {
	                Node tempTempNode = null;
	                while (tempIter.hasMoreNodes()) {
	                    tempTempNode = tempIter.nextNode();
	                    if (tempTempNode instanceof TableColumn) break;
	                }
	                if (tempTempNode != null && tempTempNode instanceof TableColumn) {
	                    Node finalNode = tempTempNode.getFirstChild();
	                    String result = null;
	                    if (finalNode == null) {
	                        result = "";
	                    } else result = finalNode.getText();
	                    switch (i) {
	                        case 0:
	                            if (result == null)
	                                score.courseId = -1;
	                            else
	                                try {
	                                    score.courseId = Long.parseLong(result);

	                                } catch (NumberFormatException e) {
	                                    score.grade = -1;
	                                }
	                            break;
	                        case 1:
	                            score.courseName = result;
	                            break;
	                        case 2:
	                            score.courseType = result;
	                            break;
	                        case 3:
	                            if (result == null)
	                                score.credit = -1;
	                            else
	                                try {
	                                    score.credit = Double.parseDouble(result);
	                                } catch (NumberFormatException e) {
	                                    score.grade = -1;
	                                }
	                            break;
	                        case 4:
	                            score.teacherName = result;
	                            break;
	                        case 5:
	                            score.academy = result;
	                            break;
	                        case 6:
	                            score.studyType = result;
	                            break;
	                        case 7:
	                            score.year = result;
	                            break;
	                        case 8:
	                            score.semester = result;
	                            break;
	                        case 9:
	                            if (result == null)
	                                score.grade = -1;
	                            else
	                                try {
	                                    score.grade = Double.parseDouble(result);
	                                } catch (NumberFormatException e) {
	                                    score.grade = -1;
	                                }
	                            break;
	                    }
	                }
	            }
	            list.add(score);
	        }

	        return list;
	    }

}
