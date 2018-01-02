/* document ready
/***********************************************************/
$(function() {
    /* render exercise from exerciseData (function)
    /* param: pageNum
    /*******************************************/

    // return ascii or char
    var getLetterOrder = function(order) {
        if (typeof(order) == "string") {
            return order.charCodeAt();
        } else if (typeof(order) == "number") {
            return String.fromCharCode(order+97);
        } else {
            return order;
        }
    };
    function renderExercise(pageNum) {
        $("#curPage").val(pageNum);
        var dataLen = exerciseData.length;
        var exerciseL = null;
        var exerciseR = null;
        var totalPage = (dataLen % 2 === 0) ? (Math.floor(dataLen / 2)) :
            (Math.floor(dataLen / 2) +
                1);

        if (curPage > dataLen / 2) {
            $("#pagenext").hide();
            $("#pagesubmit").show();
        }

        if (curPage == totalPage && dataLen % 2 !== 0) {
            exerciseL = exerciseData[curPage * 2 - 2];
            $("#exerciseLeft").html(exerciseL.no + ".&nbsp;" +
                exerciseL.title);
            $("#curLeftNo").val(exerciseL.no);
            $("#curRightNo").val("-1");
            for (var i = 0; i < exerciseL.items.length; i++) {
                $("#exerciseListLeft").append(
                    "<input type='radio' data-role='none' name='exercise-l' id='item-l-" +
                    (i + 1) + "'>" +
                    "<label for='item-l-" + (i + 1) +
                    "'>&nbsp;&nbsp;" + getLetterOrder(i) + ")&nbsp;" +
                    exerciseL.items[i] + "</label><br>");
            }
        } else {
            exerciseL = exerciseData[curPage * 2 - 2];
            exerciseR = exerciseData[curPage * 2 - 1];

            $("#exerciseLeft").html(exerciseL.no + ".&nbsp;" +
                exerciseL.title);
            $("#curLeftNo").val(exerciseL.no);
            for (var i = 0; i < exerciseL.items.length; i++) {
                $("#exerciseListLeft").append(
                    "<input type='radio' data-role='none' name='exercise-l' id='item-l-" +
                    (i + 1) + "'>" +
                    "<label for='item-l-" + (i + 1) +
                    "'>&nbsp;&nbsp;" + getLetterOrder(i) + ")&nbsp;" +
                    exerciseL.items[i] + "</label><br>");
            }

            $("#exerciseRight").html(exerciseR.no + ".&nbsp;" +
                exerciseR.title);
            $("#curRightNo").val(exerciseR.no);
            for (var i = 0; i < exerciseR.items.length; i++) {
                $("#exerciseListRight").append(
                    "<input type='radio' data-role='none' name='exercise-r' id='item-r-" +
                    (i + 1) + "'>" +
                    "<label for='item-r-" + (i + 1) +
                    "'>&nbsp;&nbsp;" + getLetterOrder(i) + ")&nbsp;" +
                    exerciseR.items[i] + "</label><br>");
            }
        }
    }
    /* initData (function)
    /*******************************************/
    function InitData() {
        EmptyData();
        $("#eprogressShape").css("width", 0);
        $("#eprogressPoint").css("left", 0);

        $("#curPage").val("1");
        $("#curScore").val("0");
        $("#curLeftNo").val("1");
        $("#curRightNo").val("2");

        $("#pagenext").show();
        $("#pagesubmit").hide();
        renderExercise(curPage);
    }
    /* emptyData (function)
    /*******************************************/
    function EmptyData() {
        $("#exerciseListLeft").empty();
        $("#exerciseLeft").empty();
        $("#exerciseListRight").empty();
        $("#exerciseRight").empty();
    }
    /* testing progress (function)
    /*******************************************/
    function RunProgress() {
        // total pages
        var getPercent = 0;
        var dataLen = exerciseData.length;

        if (arguments.length <= 0) {
            getPercent = ($("#curLeftNo").val() / dataLen * 100) - 1;
        } else {
            getPercent = (arguments[0] / dataLen * 100) - 1;
        }

        $("#eprogressShape").animate({
            width: getPercent + "%"
        }, 300);
        $("#eprogressPoint").animate({
            left: getPercent + "%"
        }, 300);
    }
    /* get current score (function)
    /*******************************************/
    function GetTotalScore() {
        var curScore = $("#curScore").val();
        var curPage = $("#curPage").val();
        var curLeftNo = $("#curLeftNo").val();
        var curRightNo = $("#curRightNo").val();
        var leftValue = '-1',
            rightValue = '-1';

        var dataLen = exerciseData.length;
        var totalPage = (dataLen % 2 === 0) ? (Math.floor(dataLen /
                2)) :
            (Math.floor(dataLen / 2) +
                1);
        var isLeftChecked = $(
            "#exerciseListLeft > input:radio[name='exercise-l']"
        ).is(":checked");
        var isRightChecked = $(
            "#exerciseListRight > input:radio[name='exercise-r']:checked"
        ).is(":checked");
        if (isLeftChecked) {
            leftValue = $(
                "#exerciseListLeft > input:radio[name='exercise-l']:checked"
            ).attr("id").split("-")[2];
        } else {
            return -1;
        }
        if (isRightChecked) {
            rightValue = $(
                "#exerciseListRight > input:radio[name='exercise-r']:checked"
            ).attr("id").split("-")[2];
        } else if (curPage == totalPage && dataLen % 2 !== 0) {
            return 0;
        } else {
            return -1;
        }

        if (leftValue == exerciseData[curLeftNo - 1].answer) {
            $("#curScore").val(++curScore);
        }
        if (rightValue == exerciseData[curRightNo - 1].answer) {
            $("#curScore").val(++curScore);
        }
        //console.log(curScore);
    }
    /* give rating according to the current score (function)
    /*******************************************/
    function GiveLevel() {
        var curScore = $("#curScore").val();
        for (var i = 0; i < levelRule.length; i++) {
            if (Number(curScore) >= Number(levelRule[i].min) && Number(
                    curScore) <= Number(levelRule[i].max)) {
                return levelRule[i].level;
            }
        }
        return null;
    }

    /* event
    /*******************************************/
    $(".exercise .exercise-list").on("change", "input[type=radio]", function() {
        var isLeftChecked = $("#exerciseListLeft input:checked").index() == -1 ?
            false : true;
        var isRightChecked = $("#exerciseListRight input:checked").index() == -1 ?
            false : true;
        //console.log(isLeftChecked + "," + isRightChecked);
        var curLeftNo = $("#curLeftNo").val();
        var curRightNo = $("#curRightNo").val();
        if ((isLeftChecked && !isRightChecked) || (!isLeftChecked && isRightChecked)) {
            RunProgress(curLeftNo);
        } else if (isLeftChecked && isRightChecked) {
            RunProgress(curRightNo);
        } else {
            return;
        }
    });
    $("#pagenext").click(function() {
        if (GetTotalScore() < 0) {
            $("#eTips").show();
            return;
        } else {
            $("#eTips").hide();
        }
        EmptyData();
        //RunProgress();
        renderExercise(++curPage);
    });
    $("#level-start").click(function() {
        $("#lt-start").hide();
        $("#lt-go").show();
    });
    $("#level-restart").click(function() {
        $("#lt-end").hide();
        $("#lt-start").show();
    });
    $("#pagesubmit").click(function() {
        if (GetTotalScore() < 0) {
            return;
        }
        
        var level = GiveLevel();
        
        //将测试的综合分数，异步提交到后台保存
        $.ajax({
			type : "POST", //post提交方式默认是get
			dataType : 'json',
			timeout : 8000, //超时时间8秒
			data : {
				test_level : level
			},
			url : basePath + '/ucenter/user/initUserTestLevel',
			error : function(jqXHR, textStatus, errorThrown) {
			},
			success : function(result) {//提交成功
			}
		});
        
        //RunProgress();
        setTimeout(function() {
            $("#lt-go").hide();
            if (GiveLevel() !== null) {
                $("#curLevel").html("Module&nbsp;" +
                    GiveLevel());
            }
            $("#lt-end").show();
            curPage = 1;
            InitData();
        }, 320);
    });

    /* getData
    /*******************************************/
    var levelRule = [{
        "min": "0",
        "max": "5",
        "level": "1"
    }, {
        "min": "6",
        "max": "11",
        "level": "2"
    }, {
        "min": "12",
        "max": "16",
        "level": "3"
    }, {
        "min": "17",
        "max": "21",
        "level": "4"
    }, {
        "min": "22",
        "max": "25",
        "level": "5"
    }];
    var exerciseData = [{
        "no": "1",
        "title": "They didn’t express regret over the accident, and didn’t send their condolences _____ .",
        "items": ["either", "too", "nor", "neither",
            "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "2",
        "title": "We ______ aware of the low exchange rates before we went.",
        "items": ["should have been", "should be",
            "should have to be", "should have",
            "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "3",
        "title": "As she ______ out of the taxi, it started to thunder.",
        "items": ["gets", "was getting", "has gotten",
            "getting", "I don't know."
        ],
        "answer": "2"
    }, {
        "no": "4",
        "title": "We _____ go bowling as often as we do now.",
        "items": ["didn’t used to", "aren’t used to",
            "not used", "didn’t use to",
            "I don't know."
        ],
        "answer": "4"
    }, {
        "no": "5",
        "title": "This is one of the _____ expensive diamonds in _____.",
        "items": ["less/Japan", "least/Japan",
            "lesser/Japanese", "least/Japanese",
            "I don't know."
        ],
        "answer": "2"
    }, {
        "no": "6",
        "title": "_____ of _____ pens should Catherine use to write the letter?",
        "items": ["Which/hers", "What/hers",
            "What/her", "Which/her",
            "I don't know."
        ],
        "answer": "4"
    }, {
        "no": "7",
        "title": "He ate the _____ in the _____ .",
        "items": ["desert/dessert", "dessert/dessert",
            "dessert/desert", "desert/desert",
            "I don't know."
        ],
        "answer": "3"
    }, {
        "no": "8",
        "title": "The _____ water is in the field, and _____ water is over there.",
        "items": ["horses'/Jane's", "horse's/Janes", "horses'/Janes",
            "horses/Jane's", "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "9",
        "title": "Although I studied physics _____ I could get a better job, _____ I eventually quit.",
        "items": ["so that/&empty;", "in order to/&empty;",
            "so that/but", "in order/&empty;",
            "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "10",
        "title": "She was the woman _____ felt _____ during astronomy class.",
        "items": ["which/boring", "which/bored",
            "who/boring",
            "who/bored", "I don't know."
        ],
        "answer": "4"
    }, {
        "no": "11",
        "title": "Yesterday I _____ the market to buy three _____ .",
        "items": ["go to/ducks", "go to/duck",
            "went to/ducks", "went to/duck",
            "I don't know."
        ],
        "answer": "3"
    }, {
        "no": "12",
        "title": "The courier swore he would _____ the medical supplies to my dorm mate and _____ over the holiday.",
        "items": ["bring/I", "take/me", "take/I",
            "bring/me",
            "I don't know"
        ],
        "answer": "4"
    }, {
        "no": "13",
        "title": "_____ people don't have _____ things.",
        "items": ["Many/many",
            "Much/much", "Many/much",
            "Much/many", "I don't konw."
        ],
        "answer": "1"
    }, {
        "no": "14",
        "title": "I did research on the species at the university library. According to the files _____, _____ average life span is only three years.",
        "items": ["there/its", "their/its", "they're/it's",
            "there/it's", "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "15",
        "title": "I used _____ at _____.",
        "items": ["the debit card/the ATM",
            "debit card/ATM",
            "debit card/the ATM", "the debit card/ATM",
            "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "16",
        "title": "Could you _____ imagined he could _____ us from way over there?",
        "items": ["have/here",
            "of/here",
            "of/hear", "have/hear",
            "I don't know."
        ],
        "answer": "4"
    }, {
        "no": "17",
        "title": "My aunt mentioned how living in the big city should both _____ my resume, and help get me _____ ahead in my career.",
        "items": ["compliment/further",
            "complement/further",
            "complement/farther", "compliment/father",
            "I don't know."
        ],
        "answer": "2"
    }, {
        "no": "18",
        "title": "I remember that I _____ the fan in _____ yellow waiting room.",
        "items": ["turned on/an", "opened/an",
            "turned on/a",
            "opened/a", "I don't know."
        ],
        "answer": "3"
    }, {
        "no": "19",
        "title": "When I asked if he could _____ me £50, he apologized and said that his English was not very _____.",
        "items": ["borrow/good", "lend/well", "lend/good",
            "borrow/well", "I don't know."
        ],
        "answer": "3"
    }, {
        "no": "20",
        "title": "_____ nine o'clock, I had contacted the company _____ telephone.",
        "items": ["At/by", "On/by",
            "In/for", "By/by",
            "I don't know."
        ],
        "answer": "4"
    }, {
        "no": "21",
        "title": "I discovered that the _____ of naval officers was _____ than our spies had reported.",
        "items": ["amount/fewer",
            "amount/less",
            "number/fewer",
            "number/less", "I don't know."
        ],
        "answer": "3"
    }, {
        "no": "22",
        "title": "The director urged me to _____ some PowerPoint slides for the presentation, but also stressed that I _____ everything.",
        "items": ["make/cite", "do/cite", "do/site",
            "make/site", "I don't know."
        ],
        "answer": "1"
    }, {
        "no": "23",
        "title": "I stressed at his funeral that we _____ each other since we _____ classmates at the academy.",
        "items": ["known/are", "have known/were",
            "had known/were", "are knowing/were",
            "I don't know."
        ],
        "answer": "3"
    }, {
        "no": "24",
        "title": "I am saying very plainly that the enemy is better equipped _____ _____",
        "items": ["then/us", "than/us",
            "then/we", "than/we",
            "I don't know."
        ],
        "answer": "4"
    }, {
        "no": "25",
        "title": "I assured him that I was _____ , _____, I could grab my coat and leave immediately.",
        "items": ["already/e.g.", "all ready/e.g.",
            "all ready/i.e.", "already/i.e.",
            "I don't know."
        ],
        "answer": "3"
    }];

    /* init
    /*******************************************/
    var curPage = 1;
    InitData();
});
