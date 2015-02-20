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
			$post_array["month"] = $month;

		if($year != null)
			$post_array["year"] = $year;
		/*print_r($post_array);
		echo $uni_URL . $calendar_path;*/
		/*$opts = array (
				        'http' => array (
				                'method' => "POST",
				                'header' => 'Content-Type: text/xml\r\n',
				                'content' => $post_array 
				        				) 
						);
		
		$context = stream_context_create ( $opts );

		$hrtml = file_get_html($uni_URL . $calendar_path, false, $context);
	return $hrtml;*/
		//url-ify the data for the POST
		$fields_string = "";
		foreach($post_array as $key=>$value) 
		{ 
			$fields_string .= $key.'='.$value.'&'; 
		}

		rtrim($fields_string, '&');
		//print_r($post_array);
		//open connection
		$ch = curl_init();

		//set the url, number of POST vars, POST data
		curl_setopt($ch,CURLOPT_URL, $uni_URL . $calendar_path);
		curl_setopt($ch,CURLOPT_POST, count($post_array));
		curl_setopt($ch,CURLOPT_POSTFIELDS, $fields_string);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

		//execute post
		$result = curl_exec($ch);

		//close connection
		curl_close($ch);
		return str_get_html($result); ;
	}

	function GetInformations($day, $month, $year){
		$hrtml = GetHTMLCalendar($day, $month, $year);
		$el = $hrtml->find('table[id=timetable]', 0);
		$es = $el;
		$rows = array();
		$array_result = array();

		$hr = 8;
		$min = 0;
		$inc = 30;
		$found = false;

		foreach ($el->find('tr') as $row ) {
			if (strpos($row->innertext, 'TSAC') !== false)
    			array_push($rows, $row);
		}

		foreach ($rows as $r) {
			foreach ($r->find('td') as $col) {
				if($col->class != "room"){

					if($col->colspan != null && strpos($col->innertext, 'TSAC') != false){
						$item = array();
						$item["starth"] = $hr;
						$item["startm"] = $min;
						$item["descrizione"] = $col->find('a', 0)->innertext;
						$mult = $col->colspan * $inc;
						$hr += floor($mult / 60);
						$min += $mult % 60;
						$item['endh'] = $hr;
						$item['endm'] = $min;

						array_push($array_result, $item);
					}
					else{
						if($min == $inc){
							$hr++;
							$min = 0;
						}
						else{
							$min += $inc;
							if($min == 60){
								$hr++;
								$min = 0;
							}
						}
					}
					
					
				}
			}
		}

		print_r($array_result);

		return $es;

	}



?>