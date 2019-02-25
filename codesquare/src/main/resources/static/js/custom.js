
/* JS Document */

/******************************

[Table of Contents]

1. Vars and Inits
2. Set Header
3. Init Menu
4. Init Search
5. Init Gallery


******************************/

$(document).ready(function()
{
	"use strict";

	/* 

	1. Vars and Inits

	*/
	$('body').scrollspy({target: ".lecture-navbar"})
	var map;
	var header = $('.header');
	var menu = $('.menu');
	var menuActive = false;

	setHeader();

	$(window).on('resize', function()
	{
		setHeader();
	});

	$(document).on('scroll', function()
	{
		setHeader();
	});

	initMenu();
	initSearch();
	initGallery();


	/* 

	2. Set Header

	*/

	function setHeader()
	{
		if($(window).scrollTop() > 91)
		{
			header.addClass('scrolled');
		}
		else
		{
			header.removeClass('scrolled');
		}
	}

	/* 

	3. Init Menu

	*/

	function initMenu()
	{
		if($('.hamburger').length && $('.menu').length)
		{
			var hamb = $('.hamburger');

			hamb.on('click', function()
			{
				if(!menuActive)
				{
					openMenu();
				}
				else
				{
					closeMenu();
				}
			});
		}
	}

	function openMenu()
	{
		menu.addClass('active');
		menuActive = true;
	}

	function closeMenu()
	{
		menu.removeClass('active');
		menuActive = false;
	}

	/* 

	4. Init Search

	*/

	function initSearch()
	{
		if($('.search_dropdown').length)
		{
			var dds = $('.search_dropdown');
			dds.each(function()
			{
				var dd = $(this);
				if(dd.find('ul > li').length)
				{
					var ddItems = dd.find('ul > li');
					dd.on('click', function()
					{
						dd.toggleClass('active');
					});
					ddItems.each(function()
					{
						var ddItem = $(this);
						ddItem.on('click', function()
						{
							dd.find('span').text(ddItem.text());
						});
					});
				}	
			});
		}
	}

	/* 

	5. Init Gallery

	*/

	function initGallery()
	{
		if($('.gallery_slider').length)
		{
			var gallerySlider = $('.gallery_slider');
			gallerySlider.owlCarousel(
			{
				items:5,
				autoplay:false,
				loop:true,
				nav:false,
				dots:false,
				smartSpeed:1200,
				margin:15,
				responsive:
				{
					0:
					{
						items:1
					},
					576:
					{
						items:2
					},
					768:
					{
						items:3
					},
					992:
					{
						items:4
					},
					1440:
					{
						items:5
					}
				}
			});
		}

		if($('.gallery_item').length)
		{
			$('.colorbox').colorbox(
			{
				rel:'colorbox',
				photo: true,
				maxWidth:'95%',
				maxHeight:'95%'
			});
		}
	}


	
/*	var waypoints = $('#footer').waypoint({
		  handler: function() {
		    $("#overview").css('position','fixed').css('right','100px')
		    
		  },
		  context: '#overflow-scroll-offset',
		  offset: '100%'
		})*/
//    $( window ).scroll( function() {
//        if ( $( document ).scrollTop() > lectureHeaderOffset.top ) {
//          $('.lecture_header_item').addClass('lecture-header-top');
//        }
//        else {
//          $('.lecture_header_item').removeClass('lecture-header-top');
//        }
//      });


});
//비동기적 단어 필터링 메서드
function checkKeywordFilter(content){
	var arr=new Array();
	$.ajax({
		url : '/Board/filter/data',
		type: 'GET',
		contentType : "application/json; charset=utf-8",
		async:false,
		data : {"content":content}
	}).done(function(data) {
		$.each(data,function(index,item){
			arr[index]=item;
		})
		
	}).fail(function(data) {
		alert("Keyword Load Failed");
	})
	return arr;
}


