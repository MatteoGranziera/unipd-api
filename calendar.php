<?php
	include 'simple_html_dom.php';
	$uni_URL = 'http://www.unipordenone.it';
	$calendar_path = '/mrbs/day.php';

	function GetHTMLCalendar($day, $month, $year){
		global $uni_URL, $calendar_path;
		$post_array = array();

		if($day != null)
			$post_array["day"] = $day;

		if($month != null)
			$post_array["month"] = $day;

		if($year != null)
			$post_array["year"] = $day;
		/*print_r($post_array);
		echo $uni_URL . $calendar_path;*/
		/*$opts = array (
				        'http' => array (
				                'method' => "POST",
				                'header' => 'Content-Type: text/xml\r\n',
				                'content' => $post_array 
				        				) 
						);
		
		$context = stream_context_create ( $opts );*/

		//$html = file_get_html($uni_URL . $calendar_path, false, $context);

		//url-ify the data for the POST
		foreach($post_array as $key=>$value) 
		{ 
			$fields_string .= $key.'='.$value.'&'; 
		}
		rtrim($fields_string, '&');

		//open connection
		$ch = curl_init();

		//set the url, number of POST vars, POST data
		curl_setopt($ch,CURLOPT_URL, $uni_URL . $calendar_path);
		curl_setopt($ch,CURLOPT_POST, count($fields));
		curl_setopt($ch,CURLOPT_POSTFIELDS, $fields_string);

		//execute post
		$result = curl_exec($ch);

		//close connection
		curl_close($ch);
		return str_get_html($str); ;
	}

	function GetInformations($day, $month, $year){
		
		$html = GetHTMLCalendar($day, $month, $year);

	}



?>