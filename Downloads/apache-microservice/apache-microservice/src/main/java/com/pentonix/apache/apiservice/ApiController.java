package com.pentonix.apache.apiservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pentonix.apache.repository.*;

@RestController
public class ApiController {
	
	@Autowired
	private final ApiDataService apiDataService;
	
	@Autowired
	private DynamoDbRepository repository;
	
	
	
	@Autowired
	public ApiController(ApiDataService apiDataService) {
		this.apiDataService = apiDataService;
	}
	
	@GetMapping()
	public String getValueFromPath() {
		return "Welcome to Apache POI Server.";
	}
	
	
	@GetMapping(path="/api/workflow/excel-data-read/getMainWithSub")
	public Object excelDataMainWithSub(@RequestParam String workflowId,@RequestParam String filename) throws InvalidFormatException {
		System.out.println("From new method");
		WorkflowMaster workflow = repository.getWorkflow(workflowId);
		List<MainWithSubNodeResponse> mainWithSub = new ArrayList<>();
		int noNodes = workflow.getNodes();
		for( int j =0;j < noNodes ;j++ ) {
			List values = new ArrayList<>();
			values = apiDataService.readExcelFileMain(filename);
			MainSheet sheet = (MainSheet) values.get(j);
			int i;
			String targetNode = sheet.getNode();
			
			for(i = 0 ;i < values.size()-1;i++) {
				sheet = (MainSheet) values.get(i);
				if( sheet.getNode().toString().equals( targetNode ) ) {
					break;
				}
			}

			String str = sheet.getLayout();		
			System.out.println(str);
			String[] arrOfStr = str.split("##");
			
			List textArr = new ArrayList<>();
			List imageArr = new ArrayList<>();
			List radioArr = new ArrayList<>();
			List commentsArr = new ArrayList<>();
			List bulletTextArr = new ArrayList<>();
			List buttonArr = new ArrayList<>();
			List textWithSearchArr = new ArrayList<>();
			
			System.out.println("Size: "+arrOfStr.length);
			for( int l =0;l < arrOfStr.length;l++ ) {
				String[] arrOfSubStr = arrOfStr[l].split("-");
				System.out.println(arrOfSubStr[0]);
				
				if( arrOfSubStr[0].toLowerCase().equals("bt") ) {
					// bullet text
					bulletTextArr.add(excelDataTextTypeById("BulletText",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("b") ) {
					// button
					buttonArr.add(excelDataTextTypeById("Button",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("ts") ) {
					// textwithsearchicon
					textWithSearchArr.add(excelDataTextTypeById("TextWithSearch",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("t") ) {
					textArr.add(excelDataTextTypeById("Text",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("ia") || arrOfSubStr[0].toLowerCase().equals("ib") 
						|| arrOfSubStr[0].toLowerCase().equals("ic") || arrOfSubStr[0].toLowerCase().equals("id") 
						|| arrOfSubStr[0].toLowerCase().equals("ie") ) {
					imageArr.add(excelDataTextTypeById("Image",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("r") ) {
					radioArr.add(excelDataRadioById(Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("c") ) {
					commentsArr.add(excelDataTextTypeById("Comments",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
			}
			
			String header = sheet.getHeader();
			String[] arrOfheader = header.split("##");
			List headerArr = new ArrayList<>();
			
			for( String eachHeader : arrOfheader ) {
				String[] arrOfSubHeader = eachHeader.split("-");
				headerArr.add(excelDataTextTypeById("Header",Integer.parseInt(arrOfSubHeader[1]),filename) );
			}
			
			MainWithSubNodeResponse response = new MainWithSubNodeResponse();
			response.setHeader(headerArr);
			response.setLayout(sheet.getLayout());
			response.setText(textArr);
			response.setBulletText(bulletTextArr);
			response.setButton(buttonArr);
			response.setTextWithSearch(textWithSearchArr);
			response.setImage(imageArr);
			response.setRadio(radioArr);
			response.setComments(commentsArr);
			response.setNextNode(sheet.getNextNode());
			response.setNode(sheet.getNode());
			response.setNodename(sheet.getNodename());
			response.setPrevNode(sheet.getPrevNode());
			response.setScreentype(sheet.getScreentype());
			
			List subNodeValues = new ArrayList<>();
			sheet = (MainSheet)values.get(i);
			if( !sheet.getSubNode().equals("null") ) {
				values = new ArrayList<>();
				values = apiDataService.readExcelFileSubNode(filename);
				MainSheet sheetSubNode = (MainSheet) values.get(0);
				
				for(i = 0 ;i < values.size()-1;i++) {
					sheetSubNode = (MainSheet) values.get(i);
					if( sheetSubNode.getNode() != null && sheetSubNode.getSubNode().toString().equals( targetNode ) ) {
						subNodeValues.add(sheetSubNode);
					}
				}
			}
			response.setSubNode(subNodeValues);
			mainWithSub.add(response);
		}
		return mainWithSub;
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/{nodeNo}")
	public Object excelData(@PathVariable String nodeNo,@RequestParam String filename) throws InvalidFormatException {
		if( characterCount(nodeNo) == 2 ) {
			//reading subnode main
			List values = new ArrayList<>();
			values = apiDataService.readExcelFileSubNode(filename);
			MainSheet sheet = (MainSheet) values.get(0);
			int i;

			for(i = 0 ;i < values.size()-1;i++) {
				sheet = (MainSheet) values.get(i);
				if( sheet.getNode().toString().equals( nodeNo.toString() ) ) {
					break;
				}
			}	
			sheet = (MainSheet) values.get(i);
			
			String str = sheet.getLayout();		
			String[] arrOfStr = str.split("##");
			
			List textArr = new ArrayList<>();
			List imageArr = new ArrayList<>();
			List radioArr = new ArrayList<>();
			List commentsArr = new ArrayList<>();
			List bulletTextArr = new ArrayList<>();
			List buttonArr = new ArrayList<>();
			List textWithSearchArr = new ArrayList<>();
			
			for( String eachStr : arrOfStr ) {
				String[] arrOfSubStr = eachStr.split("-");
				
				if( arrOfSubStr[0].toLowerCase().equals("bt") ) {
					// bullet text
					bulletTextArr.add(excelDataTextTypeById("BulletText",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("b") ) {
					// button
					buttonArr.add(excelDataTextTypeById("Button",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("ts") ) {
					// textwithsearchicon
					textWithSearchArr.add(excelDataTextTypeById("TextWithSearch",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("t") ) {
					textArr.add(excelDataTextTypeById("Text",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("ia") || arrOfSubStr[0].toLowerCase().equals("ib") 
						|| arrOfSubStr[0].toLowerCase().equals("ic") || arrOfSubStr[0].toLowerCase().equals("id") 
						|| arrOfSubStr[0].toLowerCase().equals("ie") ) {
					imageArr.add(excelDataTextTypeById("Image",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("r") ) {
					radioArr.add(excelDataRadioById(Integer.parseInt(arrOfSubStr[1]),filename) );
				}
				if( arrOfSubStr[0].toLowerCase().equals("c") ) {
					commentsArr.add(excelDataTextTypeById("Comments",Integer.parseInt(arrOfSubStr[1]),filename) );
				}
			}
			
			String header = sheet.getHeader();
			String[] arrOfheader = header.split("##");
			List headerArr = new ArrayList<>();
			
			for( String eachHeader : arrOfheader ) {
				String[] arrOfSubHeader = eachHeader.split("-");
				headerArr.add(excelDataTextTypeById("Header",Integer.parseInt(arrOfSubHeader[1]),filename) );
			}
			SubNodeResponse response = new SubNodeResponse();
			response.setHeader(headerArr);
			response.setLayout(sheet.getLayout());
			response.setText(textArr);
			response.setBulletText(bulletTextArr);
			response.setButton(buttonArr);
			response.setTextWithSearch(textWithSearchArr);
			response.setImage(imageArr);
			response.setRadio(radioArr);
			response.setComments(commentsArr);
			response.setNextNode(sheet.getNextNode());
			response.setNode(sheet.getNode());
			response.setNodename(sheet.getNodename());
			response.setPrevNode(sheet.getPrevNode());
			response.setScreentype(sheet.getScreentype());
			response.setMainNode(sheet.getSubNode());
			
			return response;
		}else {
			// reading main
		List values = new ArrayList<>();
		values = apiDataService.readExcelFileMain(filename);
		MainSheet sheet = (MainSheet) values.get(0);
		int i;

		for(i = 0 ;i < values.size()-1;i++) {
			sheet = (MainSheet) values.get(i);
			if( sheet.getNode().toString().equals( nodeNo.toString() ) ) {
				break;
			}
		}
		
		sheet = (MainSheet) values.get(i);
		String str = sheet.getLayout();		
		String[] arrOfStr = str.split("##");
		
		List textArr = new ArrayList<>();
		List imageArr = new ArrayList<>();
		List radioArr = new ArrayList<>();
		List commentsArr = new ArrayList<>();
		
		for( String eachStr : arrOfStr ) {
			String[] arrOfSubStr = eachStr.split("-");
			
			if( arrOfSubStr[0].toLowerCase().equals("t") ) {
				textArr.add(excelDataTextTypeById("Text",Integer.parseInt(arrOfSubStr[1]),filename) );
			}
			if( arrOfSubStr[0].toLowerCase().equals("ia") || arrOfSubStr[0].toLowerCase().equals("ib") 
					|| arrOfSubStr[0].toLowerCase().equals("ic") || arrOfSubStr[0].toLowerCase().equals("id") 
					|| arrOfSubStr[0].toLowerCase().equals("ie") ) {
				imageArr.add(excelDataTextTypeById("Image",Integer.parseInt(arrOfSubStr[1]),filename) );
			}
			if( arrOfSubStr[0].toLowerCase().equals("r") ) {
				radioArr.add(excelDataRadioById(Integer.parseInt(arrOfSubStr[1]),filename) );
			}
			if( arrOfSubStr[0].toLowerCase().equals("c") ) {
				commentsArr.add(excelDataTextTypeById("Comments",Integer.parseInt(arrOfSubStr[1]),filename) );
			}
		}
		
		String header = sheet.getHeader();
		String[] arrOfheader = header.split("##");
		List headerArr = new ArrayList<>();
		
		for( String eachHeader : arrOfheader ) {
			String[] arrOfSubHeader = eachHeader.split("-");
			headerArr.add(excelDataTextTypeById("Header",Integer.parseInt(arrOfSubHeader[1]),filename) );
		}
		
		MainWithSubNodeResponse response = new MainWithSubNodeResponse();
		response.setHeader(headerArr);
		response.setLayout(sheet.getLayout());
		response.setText(textArr);
		response.setImage(imageArr);
		response.setRadio(radioArr);
		response.setComments(commentsArr);
		response.setNextNode(sheet.getNextNode());
		response.setNode(sheet.getNode());
		response.setNodename(sheet.getNodename());
		response.setPrevNode(sheet.getPrevNode());
		response.setScreentype(sheet.getScreentype());
		
		List subNodeValues = new ArrayList<>();
		sheet = (MainSheet)values.get(i);
		if( !sheet.getSubNode().equals("null") ) {
			values = new ArrayList<>();
			values = apiDataService.readExcelFileSubNode(filename);
			MainSheet sheetSubNode = (MainSheet) values.get(0);
			
			for(i = 0 ;i < values.size()-1;i++) {
				sheetSubNode = (MainSheet) values.get(i);
				if( sheetSubNode.getSubNode() != null && sheetSubNode.getSubNode().toString().equals( nodeNo ) ) {
					subNodeValues.add(sheetSubNode);
				}
			}
		}
		response.setSubNode(subNodeValues);
//		return values.get(i);
		return response;
		}
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/subNode/{nodeNo}")
	public Object excelDataSubNode(@PathVariable String nodeNo,@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.readExcelFileSubNode(filename);
		MainSheet sheet = (MainSheet) values.get(0);
		int i;

		for(i = 0 ;i < values.size()-1;i++) {
			sheet = (MainSheet) values.get(i);
			if( sheet.getNode().toString().equals( nodeNo.toString() ) ) {
				break;
			}
		}
		return values.get(i);
	}
	
	private Integer characterCount(String inputString) {       
        HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();
        char[] strArray = inputString.toCharArray();
        for (char c : strArray) {
            if (charCountMap.containsKey(c)) {
                charCountMap.put(c, charCountMap.get(c) + 1);
            }
            else {
                charCountMap.put(c, 1);
            }
        }
        int pointCount = 0;
        for (Map.Entry entry : charCountMap.entrySet()) {
        	char str = (char) entry.getKey();
        	if( str == '.' ) {
        		pointCount = (int) entry.getValue();
        		break;
        	}
        }
		return pointCount;
    }
	
	
//	@RequestParam String filename
	@GetMapping(path="/api/workflow/excel-data-read/Main")
	public Object excelDataMainSheet(@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
//		values = apiDataService.readExcelFileMain(filename);
		
		return values;
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/{sheetName}/{id}")
	public Object excelDataTextTypeById(@PathVariable String sheetName,@PathVariable Integer id,@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.getReadExcelFile(sheetName,filename);
		TextSheet sheet = (TextSheet) values.get(0);
		int i;

		for(i = 0 ;i < values.size()-1;i++) {
			sheet = (TextSheet) values.get(i);
			if( sheet.getId().toString().equals( id.toString() ) ) {
				break;
			}
		}
		return values.get(i);
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/get/{sheetName}")
	public Object excelDataTextType(@PathVariable String sheetName,@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.getReadExcelFile(sheetName,filename);
		return values;
	}
	
	
	@GetMapping(path="/api/workflow/excel-data-read/footer/{sheetName}")
	public Object excelDataPrevNext(@PathVariable String sheetName,@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.getReadExcelFile(sheetName,filename);
		return values;
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/footer/{sheetName}/{id}")
	public Object excelDataPrevNextById(@PathVariable String sheetName,@PathVariable Integer id,@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.getReadExcelFile(sheetName,filename);
		PreviousSheet sheet = (PreviousSheet) values.get(0);
		int i;

		for(i = 0 ;i < values.size()-1;i++) {
			sheet = (PreviousSheet) values.get(i);
			if( sheet.getId().toString().equals( id.toString() ) ) {
				break;
			}
		}
		return values.get(i);
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/Radio")
	public Object excelDataRadioSheet(@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.readExcelFileRadio(filename);

		return values;
	}
	
	@GetMapping(path="/api/workflow/excel-data-read/Radio/{id}")
	public Object excelDataRadioById(@PathVariable Integer id,@RequestParam String filename) throws InvalidFormatException {
		List values = new ArrayList<>();
		values = apiDataService.readExcelFileRadio(filename);
		RadioSheet sheet = (RadioSheet) values.get(0);
		int i;

		for(i = 0 ;i < values.size()-1;i++) {
			sheet = (RadioSheet) values.get(i);
			if( sheet.getId().toString().equals( id.toString() ) ) {
				break;
			}
		}
		return values.get(i);
	}
	
}
