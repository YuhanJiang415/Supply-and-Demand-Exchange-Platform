package com.gzpclass.supdem.Controller;
import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.gzpclass.supdem.Controller.TxTsp;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/multi")
public class getMultiPath {
	@ResponseBody
	@RequestMapping("/getMultiPath")
	public  int[] path(@RequestBody float[][] distanceMatrix) throws IOException {
		int num=distanceMatrix[0].length;
		System.out.println(num);
		TxTsp ts = new TxTsp(num);
		ts.init(distanceMatrix);
		//ts.printinit();
		int[] result=ts.solve();
		return result;
	}
}
