// ES v2
(function() {
    var teacherInfo = {
        "John": {
            "name": "John",
            "photo": ""
        },
        "Eric": {
            "name": "Eric",
            "photo": ""
        }
    };
    var englishStudioHistoryList = {

    };

    // @param {eng_studio_status} 2:可进入教室;1:不可进入教室;0:数据已过期
    var englishStudioTestingList = {
        "dataConfig" : { //单位分钟
            "timeOpeningClass" : 5,//上课前可提前进入教室的时间
            "timeClosingClass" : 50//上课后可延续进入教室的时间
        },
        "engStudioList" : [{
            "eng_studio_name" : "Testing",
            "eng_studio_description" : "逛动物园的时候如果小孩问你这个动物用英语怎么说？学习这些基本的英语避免只能尴尬一笑的处境！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-29 11:30",
            "eng_studio_start_time" : 1456716600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/anmials.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/a07ae1f7cfddac74651a4d3f&c=auto"
        }, {
            "eng_studio_name" : "Testing",
            "eng_studio_description" : "Just Testing",
            "eng_studio_duration" : 20,
            //"eng_studio_start_time" : "2016-02-29 11:30",
            "eng_studio_start_time" : 1456716600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/anmials.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/a07ae1f7cfddac74651a4d3f&c=auto"
        }, {
            "eng_studio_name" : "Anmials",
            "eng_studio_description" : "逛动物园的时候如果小孩问你这个动物用英语怎么说？学习这些基本的英语避免只能尴尬一笑的处境！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-01-21 19:00",
            "eng_studio_start_time" : 1453374000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/anmials.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/a07ae1f7cfddac74651a4d3f&c=auto"
        }, {
            "eng_studio_name" : "Pets",
            "eng_studio_description" : "如果你有宠物或者身边的人有宠物，能用流利地英语来表达你对这些小动物的感情就厉害了。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-01-23 19:00",
            "eng_studio_start_time" : 1453546800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/pets.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/ac517e92928c8b2d23f4f952&c=auto"
        }, {
            "eng_studio_name" : "Annoying office habits",
            "eng_studio_description" : "如果你的同事在办公时有很多坏习惯，学会用英语表达你的不愉快吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-01-26 19:00",
            "eng_studio_start_time" : 1453806000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/annoying-office-habits.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/f72a245bad231b574061ee48&c=auto"
        }, {
            "eng_studio_name" : "Business trip I",
            "eng_studio_description" : "出差时避免当一个trouble maker，所以学习一下常用的词汇句子，才能让你出差的过程更顺利、舒心。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-01-28 19:00",
            "eng_studio_start_time" : 1453978800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/business-trip-I.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/f7020e699d2eaee881ebf3bc&c=auto"
        }, {
            "eng_studio_name" : "Business trip II",
            "eng_studio_description" : "出差时接待外宾该说些什么不尴尬？在国外遇到棘手的问题怎么表达？",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-01-30 19:00",
            "eng_studio_start_time" : 1454151600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/business-trip-II.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/dcf6b76fc72138b2e829634d&c=auto"
        }, {
            "eng_studio_name" : "Dress code",
            "eng_studio_description" : "在办公室什么样的穿着才恰当？学习用英文如何表达你关于办公室穿着的问题。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-02 19:00",
            "eng_studio_start_time" : 1454410800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/dress-code.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/027cd5eac02ea293acf7d58d&c=auto"
        }, {
            "eng_studio_name" : "Is Google the best place to work",
            "eng_studio_description" : "谷歌是财富杂志评选为最好的办公地点。那么你上班的公司设施设备、员工福利又怎么样？用英语交流一下吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-04 19:00",
            "eng_studio_start_time" : 1454583600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Is-Google-the-best-place-to-work.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/b498e4ce86ed91a8213d6a7a&c=auto"
        }, {
            "eng_studio_name" : "Jobs&Skills I",
            "eng_studio_description" : "三百六十行，行行出状元，工作那么多，英语来表达。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-16 19:00",
            "eng_studio_start_time" : 1455620400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Jobs-skills-I.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/771356dd93e208dffaf15a7d&c=auto"
        }, {
            "eng_studio_name" : "Jobs&Skills II",
            "eng_studio_description" : "遇到英语面试千万别紧张，外教分分钟就能帮你忙！英语面试棒棒哒！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-18 19:00",
            "eng_studio_start_time" : 1455793200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Jobs-skills-II.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/6e95b5bbe0ad7ca0a2e272bf&c=auto"
        }, {
            "eng_studio_name" : "Punctuality",
            "eng_studio_description" : "守时很关键，重要场合万一迟到了，用英语表达怎么才能不露怯？",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-20 19:00",
            "eng_studio_start_time" : 1455966000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Punctuality.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://webi.vcube.com.cn/r/2f796762471a3e7f2b46aaac&c=auto"
        }, {
            "eng_studio_name" : "Make a First Contact",
            "eng_studio_description" : "和陌生人做自我介绍的时候只会说“my name is...”吗？不要这么low啦！来学习如何做一个吸睛的自我介绍吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-23 19:00",
            "eng_studio_start_time" : 1456225200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Make-a-First-Contact.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/708421ef4ff7134d6540874f2bf8ea27c8a9f393"
        }, {
            "eng_studio_name" : "Modern Offices",
            "eng_studio_description" : "当代办公室办公到处都是英文，阅读英文文件拒绝“睁眼瞎”。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-25 19:00",
            "eng_studio_start_time" : 1456398000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Modern-Offices.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/f68f61c205493e0eb2d04af64a69f84b1ddf7ab7"
        }, {
            "eng_studio_name" : "Reward Management",
            "eng_studio_description" : "想和老板提加薪，却不知道怎么表达，有必要来学习一下这些必要的相关词汇句子！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-02-27 19:00",
            "eng_studio_start_time" : 1456570800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Reward-Management.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/0b531a07caa84f4c2881a3c0e59e70cfc6644bf7"
        }, {
            "eng_studio_name" : "Talk about companies",
            "eng_studio_description" : "你都是如何向客户介绍你的公司的？能顺利地表达公司所有的部门、职位吗？",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-01 19:00",
            "eng_studio_start_time" : 1456830000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Talk_about_companies.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/3a51f32f0fabeaef67f9a7ae11ebcfffb4bc429f"
        }, {
            "eng_studio_name" : "Culture shock",
            "eng_studio_description" : "看到外国人互相亲吻脸颊别诧异，学习文化差异和外国人交往不失礼。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-03 19:00",
            "eng_studio_start_time" : 1457002800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Culture_Shock.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/2008831f1f783d737739934ee3941fba9a8a1ef2"
        }, {
            "eng_studio_name" : "Flea market",
            "eng_studio_description" : "跳蚤市场有好货，便宜合算别受骗！学会用英语砍价，走到哪里都方便！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-05 19:00",
            "eng_studio_start_time" : 1457175600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Flea_Market.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/0da8c9c661c788eeb8f033d4c6971409a6fb9313"
        }, {
            "eng_studio_name" : "Talk about jobs",
            "eng_studio_description" : "找工作时当别人需要了解你上一个工作，学习如何用英语跟别人具体地介绍自己的工作至关重要。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-08 19:00",
            "eng_studio_start_time" : 1457434800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Talk_about_jobs.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/84961d7f61fb01f3ea4d145edf1ce7b34126bd4b"
        }, {
            "eng_studio_name" : "Superstition",
            "eng_studio_description" : "在外国人看来13是个不好的数字，中国人认为4是很不吉祥的。学习这节课后在国外遇到这些风俗就不会困惑了",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-10 19:00",
            "eng_studio_start_time" : 1457607600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Superstition.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/b5838983ffc2ebad0166f30eebdb6fb70f09c770"
        }, {
            "eng_studio_name" : "Hair styling",
            "eng_studio_description" : "发型设计千千万，适合你的最好看！去理发如果因为表达不清楚头发被剪坏就糟了，快来学习一下怎么用英语表达各种发型吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-12 19:00",
            "eng_studio_start_time" : 1457780400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Hair_Styling.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "John",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_John.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/cbeb81b1b3babcf8fb2722b5ef056f3e7547a581"
        }, {
            "eng_studio_name" : "发音课：Pure Vowels I 前元音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-14 19:00",
            "eng_studio_start_time" : 1457953200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Pure_Vowels_I.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/f13a4fab81a6eed87e96de6154c83ed4edc14379"
        }, {
            "eng_studio_name" : "The First Day On The Job",
            "eng_studio_description" : "上班的第一天尤其重要，来学习一下上班第一天应该做些什么，怎么表现吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-15 19:00",
            "eng_studio_start_time" : 1458039600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/The_First_Day_On_The_Job.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/4ba619ec15a147725e9de75f1483f110aec3d93c"
        }, {
            "eng_studio_name" : "词汇课：饮品Drink",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-16 19:00",
            "eng_studio_start_time" : 1458126000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Drink.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/c19e37bda1e3b90ff88d5c0b53d979062d300d67"
        }, {
            "eng_studio_name" : "Tipping",
            "eng_studio_description" : "小费给多了自己会觉得心疼，给少了又害怕被人看不起。到餐厅、搭计程车等不同场景应该付的小费就不同哦！快来学习一下吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-17 19:00",
            "eng_studio_start_time" : 1458212400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Tipping.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/0336937d1131b9959c1f7706d328bd46dfd9160e"
        }, {
            "eng_studio_name" : "词汇课：时间Time",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-18 19:00",
            "eng_studio_start_time" : 1458298800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Time.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/3c141aa432f7448f92be410f7322f573cfdba1ae"
        }, {
            "eng_studio_name" : "Luxury",
            "eng_studio_description" : "偶尔小奢侈，生活大改观。改良生活做个讲究银儿吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-19 19:00",
            "eng_studio_start_time" : 1458385200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Luxury.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/4a83ad69e300102bb8343e7c6ecef703d9371f05"
        }, {
            "eng_studio_name" : "发音课：Pure Vowels II 后元音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-20 19:00",
            "eng_studio_start_time" : 1458471600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Pure_Vowels_II.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/f398bcd43124f4dd59bf8ea3cf9abc2db4d29888"
        }, {
            "eng_studio_name" : "发音课：Pure Vowels III 中元音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-21 19:00",
            "eng_studio_start_time" : 1458558000000,
            //"eng_studio_start_time" : 1458471600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Pure_Vowels_III.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/b7137863dc0c85c0cf28466ce29772c88b517d07"
        }, {
            "eng_studio_name" : "Change Jobs",
            "eng_studio_description" : "跳槽分被迫辞职型、被动拉拢型、随意无偿型等，快来学习一下跳槽时的注意事项吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-22 19:00",
            "eng_studio_start_time" : 1458644400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Change_Jobs.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/66815d362be6be83751ccc1055b5a54f02b95ea3"
        }, {
            "eng_studio_name" : "词汇课：数字Numbers",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-23 19:00",
            "eng_studio_start_time" : 1458730800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Numbers.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/74a621f3362e9a876743d4411f08bbbbeca068ab"
        }, {
            "eng_studio_name" : "Western Holidays in China",
            "eng_studio_description" : "作为一个西方的节日，圣诞节在中国也同样盛行。很多国外的节日流传到中国后发生了哪些变化呢？",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-24 19:00",
            "eng_studio_start_time" : 1458817200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Western_Holidays_in_China.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/9c415a9053ba0f91cb612c9e84ffee286d83ed9d"
        }, {
            "eng_studio_name" : "词汇课：时间和方位Date and Direction",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-25 19:00",
            "eng_studio_start_time" : 1458903600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Date_and_Direction.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/926fd78cb69be9cc6eaa118d8ebb433eee8dfaff"
        }, {
            "eng_studio_name" : "Music",
            "eng_studio_description" : "古典or流行？摇滚or爵士？民谣or蓝调？据调查，一个人喜欢听的音乐的风格和他的性格完全相同或相反。How about you?",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-26 19:00",
            "eng_studio_start_time" : 1458990000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Music.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/a29fe1a586eaadf1fb7a3e3d14a3a0bbf59d5cc3"
        }, {
            "eng_studio_name" : "Diphthongs I 合口双元音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-27 19:00",
            "eng_studio_start_time" : 1459076400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Diphthongs_I.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/a955a168939aa79ad2063641fa74faeb0cad0624"
        },
        // 2016年3月22日11:07:38新一轮课程排课
        {
            "eng_studio_name" : "发音课：Diphthongs II 集中双元音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-28 19:00",
            "eng_studio_start_time" : 1459162800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Diphthongs_II.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/15654a5b280b8c9c17c34031c2a0c4e0ff5a6c00"
        }, {
            "eng_studio_name" : "Workaholism 工作狂",
            "eng_studio_description" : "笑话：一位日本老板到中国出差告诉员工自己是Workaholism希望大家配合他。一个月后日本老板走的时候忿忿不平：你们这样加班是不人道的！^_^",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-29 19:00",
            "eng_studio_start_time" : 1459249200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Workaholism.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/bcc91689799af2777e4da2c18d3ee66d7342c211"
        }, {
            "eng_studio_name" : "词汇课：Quantity and Measures 数量和度量衡",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-30 19:00",
            "eng_studio_start_time" : 1459335600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Quantity_and_Measures.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/ac776bfe0583657a3bd94ae3c629584c9f05d5ee"
        }, {
            "eng_studio_name" : "Talking about Art浅谈艺术",
            "eng_studio_description" : "快来学习古今中外的艺术，让你的浑身都充满“艺术细菌”！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-03-31 19:00",
            "eng_studio_start_time" : 1459422000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Talking_about_Art.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/cdbae17ad864637c967f9cebff1642d704b94e62"
        }, {
            "eng_studio_name" : "词汇课：Currency货币",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-01 19:00",
            "eng_studio_start_time" : 1459508400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Currency.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/e7caefe56d014dbe19d8491eb58219a11b5a47bf"
        }, {
            "eng_studio_name" : "Online Shopping网上购物",
            "eng_studio_description" : "网上购物是把“双刃剑”：选择广泛但假货也不少，不用出门却要担心信息泄露。那么如何安全网上购物呢？",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-02 19:00",
            "eng_studio_start_time" : 1459594800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Online_Shopping.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/308b4447ebca5504aebe52af843b327d8c31b656"
        }, {
            //"eng_studio_name" : "发音课：Consonants辅音",
            "eng_studio_name" : "Spirant Consonants 摩擦辅音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-03 19:00",
            "eng_studio_start_time" : 1459681200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Consonants.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/4349a2eded95879f3013429fe79b4b9e5c00f241"
        }, {
            "eng_studio_name" : "Taboos 禁忌",
            "eng_studio_description" : "美国人忌讳数字13和黑色猫咪等，中国人送礼不送伞和戴绿色的帽子，学习一些国内外的忌讳，避免闹笑话。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-05 19:00",
            "eng_studio_start_time" : 1459854000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Taboos.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/6525919f343fbf5cd7b1e1837d237cb66341a25e"
        }, {
            "eng_studio_name" : "词汇课：Weather天气",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-06 19:00",
            "eng_studio_start_time" : 1459940400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Weather.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/1d4edf03a1ca971b3c5aa3fc7a1e81d94b3c70a5"
        }, {
            "eng_studio_name" : "Ask for help请求帮助",
            "eng_studio_description" : "危急情况别慌张，ask for help求帮忙！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-07 19:00",
            "eng_studio_start_time" : 1460026800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Ask_for_help.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/c3487bd036d175d7390824ad86bc083140318b94"
        }, {
            "eng_studio_name" : "Environment and Material环境与材料",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-08 19:00",
            "eng_studio_start_time" : 1460113200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Environment_and_Material.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/a7c6e9fce625c9cb9732033c9fa4562e0dd7239d"
        }, {
            "eng_studio_name" : "Going to the Theatre 剧院",
            "eng_studio_description" : "你知道为什么剧院的观众席呈扇形吗？这是由声音的混响效果等声学要求所决定的！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-09 19:00",
            "eng_studio_start_time" : 1460199600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Going_to_the_Theatre.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/192925bcb167360c3f96b53daa1e0789f49a5449"
        }, {
            "eng_studio_name" : "Plosive Consonants 爆破辅音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-10 19:00",
            "eng_studio_start_time" : 1460286000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Plosive_Consonants.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/9901f1cbe4c61cc8f0aa6937180e1a0f883c43bf"
        },
        
        // 4月中上旬课程排课
        {
            "eng_studio_name" : "发音课：Affricates 破擦辅音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-11 19:00",
            "eng_studio_start_time" : 1460372400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Affricates.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/421244921ff15b65df5a9400f7c8e9d17e414d97"
        }, {
            "eng_studio_name" : "Traditions传统",
            "eng_studio_description" : "书法、字画、京剧等都是中国的优良文化传统，取其精华弃其糟粕，传统文化学起来！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-12 19:00",
            "eng_studio_start_time" : 1460458800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Traditions.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/b050341e2da99ab5ad675270e8fb08aa2fbf7dcd"
        }, {
            "eng_studio_name" : "词汇课：Color颜色",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-13 19:00",
            "eng_studio_start_time" : 1460545200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Color.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/a843ffb3904eb72b75c21ecc5a3a87744fab0181"
        }, {
            "eng_studio_name" : "Gestures & Body Language 手势/肢体语言",
            "eng_studio_description" : "点头yes摇头no，见面hello摆摆手。学习一些常用的身体语言，帮助别人更好理解。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-14 19:00",
            "eng_studio_start_time" : 1460631600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Gestures-&-Body-Language.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/3f308a1730f2dba805cffc6eadc56c8a81a584ec"
        }, {
            "eng_studio_name" : "词汇课：Help 求助",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-15 19:00",
            "eng_studio_start_time" : 1460718000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Help.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/1cacf8cc30dd0e13acfe5cce85160f26653cf88c"
        }, {
            "eng_studio_name" : "Sports I 运动I",
            "eng_studio_description" : "左三圈，右三圈，脖子扭扭，屁股扭扭，早睡早起我们来做运动。健康生活，对自己负责！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-16 19:00",
            "eng_studio_start_time" : 1460804400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Sports-I.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/930680946cb8b0de8c9b901c64414ce14fe1016d"
        }, {
            "eng_studio_name" : "发音课：Nasals鼻音辅音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-17 19:00",
            "eng_studio_start_time" : 1460890800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Nasals.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/20ff19896d92edf8470827117c9628cb78b104a2"
        }, {
            "eng_studio_name" : "发音课：Laterals舌侧辅音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-18 19:00",
            "eng_studio_start_time" : 1460977200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Laterals.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/8b180fc42356f383cc8aeba01f4c2614a8187f4f"
        }, {
            "eng_studio_name" : "At The Garage 停车场",
            "eng_studio_description" : "上班开车or坐地铁？这是个问题。油价飙升，水涨船高，停车问题也成了有车一族的不能言说之痛。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-19 19:00",
            "eng_studio_start_time" : 1461063600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/At-The-Garage.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/2060e4773c1088e1e51c68dc1b41a9d1672b703d"
        }, {
            "eng_studio_name" : "词汇课：Food食物",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-20 19:00",
            "eng_studio_start_time" : 1461150000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Food.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/40dae78fd7dbb8400813a603264e58e8b388194d"
        }, {
            "eng_studio_name" : "Getting the News 获取新闻",
            "eng_studio_description" : "如何get到最新资讯？据调查，印刷媒体，数字媒体，搜索引擎和社交资讯是四种最主要的获取新闻的途径。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-21 19:00",
            "eng_studio_start_time" : 1461236400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Getting-the-News.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/6c2f3443ac1322df888d211685e17f20a60c01b0"
        }, {
            "eng_studio_name" : "词汇课：Cooking烹饪手法",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-22 19:00",
            "eng_studio_start_time" : 1461322800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Cooking.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/8a00a997eac1e210b53d7bcb40ff013eab487bc1"
        }, {
            "eng_studio_name" : "sports II 运动II",
            "eng_studio_description" : "看看QQ步数排行榜，总是排名第一的那个好友是不是又瘦又健康呢？赶紧follow他的脚步运动起来吧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-23 19:00",
            "eng_studio_start_time" : 1461409200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/sports-II.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Gary",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Gary.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/ee9721bf135db32ceab3ead16ea9d021cc7f6d00"
        }, {
            "eng_studio_name" : "发音课：Semivowels半元音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-24 19:00",
            "eng_studio_start_time" : 1461495600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Semivowels.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/c640c28306cf84c4b21855a155f1674e66df7652"
        },
        // 4月下旬和5月上旬课程排课
        {
            "eng_studio_name" : "发音课：Consonants Clusters 辅音连缀",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-25 19:00",
            "eng_studio_start_time" : 1461582000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Consonants-Clusters.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/159a861e45de1fd3fb726b97f8ab694520d8f8df"
        }, {
            "eng_studio_name" : "Bad Flight Experiences 糟糕的飞行体验",
            "eng_studio_description" : "航班延误或取消，真是耽误事儿。如何最大程度地挽回自己的损失，快来学习一下相关英文让打水漂的money还能捞回来一些！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-26 19:00",
            "eng_studio_start_time" : 1461668400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Bad_Flight_Experiences.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Steve",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Steve.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/ed09a8276a0f551b9833a3c890de72c7e2645842"
        }, {
            "eng_studio_name" : "词汇课：Kitchenware 厨房用具",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-27 19:00",
            "eng_studio_start_time" : 1461754800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Kitchenware.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/975d688b1f2bf4d9d818b9ef3c5912e007cc97f9"
        }, {
            "eng_studio_name" : "Give Instructions and Directions 提供说明和指导",
            "eng_studio_description" : "在帮助他人时，如何能准确直观地给出指导和说明也是一门学问哦！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-28 19:00",
            "eng_studio_start_time" : 1461841200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Give_Instructions_and_Directions.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/c579562953468f6338d002bae4ed7cfb3c760a8b"
        }, {
            "eng_studio_name" : "词汇课：Tableware 餐具",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-29 19:00",
            "eng_studio_start_time" : 1461927600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Tableware.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/051173b9a5319f181cd8d4532aa4b3995e25c559"
        }, {
            "eng_studio_name" : "Supermarket 超市",
            "eng_studio_description" : "国内外超市有很多不同：在国外，没有类似国内的早市或社区小蔬菜店，要买菜必须到超市，而超市一般都建在郊区，动辄几十公里。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-04-30 19:00",
            "eng_studio_start_time" : 1462014000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/supermarket.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Steve",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Steve.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/97a8e79907cdbd6b885e073bfafbf5b4fdbb0d4c"
        }, {
            "eng_studio_name" : "发音课：Syllables 音节的划分",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-02 19:00",
            "eng_studio_start_time" : 1462186800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Syllables.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/185d0f55fcb630d0929a20f484061347b5f43a4a"
        }, {
            "eng_studio_name" : "Expressing Concern I 表达关心1",
            "eng_studio_description" : "“我寄愁心与明月，随君直到夜郎西。莫愁前路无知己，天下谁人不识君”，古人用优美的古诗表达关切，你可以学习用英文来表示。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-03 19:00",
            "eng_studio_start_time" : 1462273200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Expressing_Concern_I.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Steve",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Steve.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/9f707103540a73233da71d5935b7229946217956"
        }, {
            "eng_studio_name" : "词汇课：Flavor and Gustation 味道和味觉",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-04 19:00",
            "eng_studio_start_time" : 1462359600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Flavor-and-Gustation.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/fbdc392f07530ecfb25fb1c3c4b2c7682b101015"
        }, {
            "eng_studio_name" : "Learning English 学英语",
            "eng_studio_description" : "单词背了就忘？不会用高大上的句型？写英文作文没有逻辑？这节课让我们一起学习如何学习英文！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-05 19:00",
            "eng_studio_start_time" : 1462446000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/learning_english.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Eric",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Eric.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/1736d3171e6fe6a73faa4fb61e58eb7f24af95ca"
        }, {
            "eng_studio_name" : "词汇课：Cereal 谷物",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-06 19:00",
            "eng_studio_start_time" : 1462532400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Cereal.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/dad9c8b44758c7e9234c64f71cc0739e56e586f9"
        }, {
            "eng_studio_name" : "Selfie Sticks 自拍杆",
            "eng_studio_description" : "自拍杆是多人拍照必备良品！你知道吗？自拍杆的起源很早，早在手机都还没有摄像头之前，自拍杆就已经存在了！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-07 19:00",
            "eng_studio_start_time" : 1462618800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Selfie_Sticks.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Steve",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Steve.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/78518e3b7e02074fe0d7ff69f251e427a35c9d3d"
        }, {
            "eng_studio_name" : "发音课：Liaison 连读",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-08 19:00",
            "eng_studio_start_time" : 1462705200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Liaison.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/98f84e40768ac482741b4ec387446612bf924baf"
        },
        // 5月中旬及中下旬
        {
            "eng_studio_name" : "发音课：Word-Stress 词重音",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-09 19:00",
            "eng_studio_start_time" : 1462791600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Word-Stress.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Matt",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Matt.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/edc3863dfed355b1c42928d8e3fd523f95ef5555"
        }, {
            "eng_studio_name" : "Expressing Concern II",
            "eng_studio_description" : "关怀是冬天里的太阳，能给寒冷的人以温暖；是沙漠里的清泉，能给旅行中的人以清爽；有关怀的地方，内心便是快乐的。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-10 19:00",
            "eng_studio_start_time" : 1462878000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Expressing-Concern-II.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/839376d0c900865157ea93ca29d92afa2f2c5c22"
        }, {
            "eng_studio_name" : "词汇课：Dessert",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-11 19:00",
            "eng_studio_start_time" : 1462964400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Dessert.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Annie",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Annie.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/4480ab51fe375c483714b7fd6734f41a73da7d84"
        }, {
            "eng_studio_name" : "Majors and Minors",
            "eng_studio_description" : "现代社会谁还没两把刷子？当代大学生为了多get一些技能，不仅学着主修课程，辅修课程也丝毫不懈怠。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-12 19:00",
            "eng_studio_start_time" : 1463050800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Majors-and-Minors.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/e59aa3674bd1d5f9a1f080d8f2b25e28fdd20921"
        }, {
            "eng_studio_name" : "词汇课：Fruit",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-13 19:00",
            "eng_studio_start_time" : 1463137200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Fruit.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/910333d2797986141a91ccfa9aabd6ce3ddb2939"
        }, {
            "eng_studio_name" : "Instruments",
            "eng_studio_description" : "笑话一则：某乐团分谱上有标记“此小节低头”，新来的乐手不明白什么意思，就没有做，结果...被后边的长号戳到脑袋。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-14 19:00",
            "eng_studio_start_time" : 1463223600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Instruments.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/993da75ee7ee97f0f327639f325d2cea25c319f5"
        }, {
            "eng_studio_name" : "Expressing Probability",
            "eng_studio_description" : "如何正确地表达概率和可能性的大小呢？除了百分比和平均数还有什么样的说法?",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-17 19:00",
            "eng_studio_start_time" : 1463482800000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Expressing-Probability.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/f6babba319a640b207797d67c687fed36e4258ef"
        }, {
            "eng_studio_name" : "词汇课：Vegetable",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-18 19:00",
            "eng_studio_start_time" : 1463569200000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Vegetable.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Annie",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Annie.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/7301a7d37d88b56ca2b96d25beb205cbf93e0ef4"
        }, {
            "eng_studio_name" : "Public Speaking",
            "eng_studio_description" : "学习公众演讲，克服演讲恐惧，提升演讲技巧！",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-19 19:00",
            "eng_studio_start_time" : 1463655600000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Public-Speaking.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/e729ee2883ec7bd3c1caee6e518acec634b102bf"
        }, {
            "eng_studio_name" : "词汇课：Seafood",
            "eng_studio_description" : null,
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-20 19:00",
            "eng_studio_start_time" : 1463742000000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/seafood.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Crystal",
            "teacher_type" : 0,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Crystal.png",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/c97cbc8d42317d69b8dae159f28b5edb4f33ee2c"
        }, {
            "eng_studio_name" : "Western Food and Restaurants",
            "eng_studio_description" : "了解西方餐饮文化及风俗，做一顿丰盛美味的西餐。",
            "eng_studio_duration" : 50,
            //"eng_studio_start_time" : "2016-05-21 19:00",
            "eng_studio_start_time" : 1463828400000,
            "eng_studio_end_time" : null,
            "eng_studio_img" : "skin/images/engstudio/Western-Food-and-Restaurants.jpg",
            "eng_studio_tag" : "English Studio",
            "teacher_name" : "Dylan",
            "teacher_type" : 1,//1为外教，0为中教
            "teacher_photo" : "skin/images/shteacher_Dylan.jpg",
            "eng_studio_status" : 1,
            "eng_studio_link" : "http://live-seminar.vcubeone.net/live/ap/57bb5af2c93089651901f34ac6cd3f6411b33108"
        }, 
    ]};

    var getGMTTime = function(systemTime) {// 单位时间戳毫秒级
        var tempDate = new Date();
        var temp = 6*60*60*1000;// 美国中部时间与gmt时间差,单位毫秒
        return new Date(systemTime).getTime() - temp + tempDate.getTimezoneOffset()*60*1000;
    };

    var englishStudioList = angular.module('englishStudioList', []);
    englishStudioList.controller("englishStudioListCtrl", function($scope, $http) {

        //var order = {};
        //order.order_id = angular.element("#hidden_order_id_by_split").val();
        var ACTIVITY_NAME = "MONTH_EXPERIENCE1";

        var getServerTime = function() {
            var serverTime = angular.element("#engStudioServerDate").val();// java cst time
            return getGMTTime(serverTime);
        };

        var openTime = englishStudioTestingList.dataConfig.timeOpeningClass*60*1000;
        var closeTime = englishStudioTestingList.dataConfig.timeClosingClass*60*1000;

        // console.log(getServerTime());
        var systemTime = getServerTime() || Date.parse(new Date().replace(/-/g, "/"));

        $scope.timeOpeningClass = englishStudioTestingList.dataConfig.timeOpeningClass;
        $scope.serverTime = systemTime;

        /**
         * 返回指定时间之后或之前指定分钟数的时间
         * ------------------------------------------------------
         * @param {startTime},单位为秒式时间戳
         * @param {classTime},单位为分钟数
         * @param {isPlus},true为加时间，false为减时间
         * @return {endTime},单位为秒式时间戳
        */
        var getTargetTime = function(startTime, classTime, isPlus) {
            var addingTime = classTime * 60 * 1000;
            var targetDate = new Date();
            if (isPlus) {
                targetDate.setTime(startTime + addingTime);
            } else {
                targetDate.setTime(startTime - addingTime);
            }
            var targetTime = Date.parse(targetDate.replace(/-/g, "/"));

            return targetTime;
        };

        var getLatestCountdown = function(time) {
            var oneHourTime = 1*3600*1000;
            var oneMinuteTime = 1*60*1000;
            var oneSecondTime = 1*1000;

            var latestCountdown = {};
            if (time <= 0 || time === null) {
                latestCountdown = {
                    "hour": "00",
                    "minute": "00",
                    "second": "00"
                };
                return latestCountdown;
            } else {
                var curDate = new Date(time);
                //console.log(time);
                var chour = Math.floor(time / oneHourTime);
                var cminute = Math.floor((time-Math.floor(chour)*3600*1000) / oneMinuteTime);
                var csecond = Math.floor((time - Math.floor(time / oneMinuteTime)*60*1000) / oneSecondTime);
                latestCountdown = {
                    "hour": chour.toString().length == 1 ? "0"+chour : chour,
                    "minute": cminute.toString().length == 1 ? "0"+cminute : cminute,
                    "second": csecond.toString().length == 1 ? "0"+csecond : csecond
                };
                // console.log(latestCountdown);
                return latestCountdown;
            }
        };

        var ESTimer = null;
        var countdownLogic = function(time, beginTime) {
            $scope.$apply(function() {
                $scope.latestCountdown = getLatestCountdown(beginTime);
            });
            if (time <= 0 && time > -(closeTime+openTime)) {
                $scope.$apply(function() {
                    $scope.latestModel.eng_studio_status = 2;
                });
            } else if (time <= -(closeTime+openTime)) {
                clearInterval(ESTimer);
                $scope.$apply(function() {
                    $scope.latestModel.eng_studio_status = 0;
                });
            }
        };

        var runningCountdown = function(startTime) {
            if (ESTimer !== undefined) {
                clearInterval(ESTimer);
            }

            var time = (startTime - openTime) - systemTime;
            var beginTime = startTime - systemTime;

            setTimeout(function(){
                countdownLogic(time, beginTime);
            },0);
            ESTimer = setInterval(function() {
                time -= 1000;
                beginTime -= 1000;
                countdownLogic(time, beginTime);
            }, 1000);
        };

        // give class today
        var getLatestClass = function(objModel) {
            // console.log(objModel.eng_studio_name);
            var latestModel = {
                "eng_studio_name": objModel.eng_studio_name,
                "eng_studio_img": objModel.eng_studio_img,
                "eng_studio_description": objModel.eng_studio_description,
                "eng_studio_status": objModel.eng_studio_status,
                "teacher_name": objModel.teacher_name,
                "teacher_type": objModel.teacher_type,
                "teacher_photo": objModel.teacher_photo,
                "eng_studio_link": objModel.eng_studio_link,
            };
            $scope.latestModel = latestModel;
            $scope.isTodayHasClass = true;
            runningCountdown(objModel.eng_studio_start_time);
        };

        var getViewModel = function(dataModel,name) {
            var list = [];
            for (var item in dataModel) {
                var strDateS = new Date(dataModel[item].eng_studio_start_time).toDateString();
                var strDateE = new Date(systemTime).toDateString();
                var dateDiff = Date.parse(strDateS.replace(/-/g, "/"))-Date.parse(strDateE.replace(/-/g, "/"));
                var iDays = parseInt(Math.abs(dateDiff) / 1000 / 60 / 60 /24);

                if (dateDiff === 0) {
                    dataModel[item].iDays = iDays;
                    getLatestClass(dataModel[item]);
                    //list.push(dataModel[item]);
                } else if (dateDiff > 0) {
                    dataModel[item].iDays = iDays;
                    list.push(dataModel[item]);
                } else {
                    continue;
                }
            }
            // console.log(list);
            return list;
        };

        // 进入教室
        $scope.goToESClass = function(link) {
            if ($(window).width() < 640) {
                alert("为了保障授课效果，请使用电脑登录，进入教室上课");
                return false;
            }
            // if ( $scope.latestCountdown.eng_studio_status != 2) {
            //     return false;
            // }
            window.open(link,"_blank");
        };
        $scope.esLimit = function() {
            if ($scope.isClassMore) {
                return null;
            } else {
                return 3;
            }
        };
        // 课时显示更多
        $scope.esclassMore = function() {
            $scope.isClassMore = true;
            //$scope.esLimit();
        };
        // 课时收起
        $scope.esclassFold = function() {
            $scope.isClassMore = false;
            //$scope.esLimit();
        };
        $scope.loadViewModel = function(data) {

            // 判断非会员中心页面是否有用户有资格查看ES栏目
            //$scope.isTicketsUser = true;

            // getbasePath;
            if (basePath === undefined) {
                var basePath = "http://" + (window.location+'').split('//')[1].split('/')[0];
            }

            $http({
                method: "POST",
                url: basePath + "/ucenter/redeemCode/findRedeemCode",
                dataType: "json",
                //headers: {'Content-type':'application/json;charset=UTF-8'},
                params: {
                    activity_name: ACTIVITY_NAME
                }
            }).success(function(data, status) {
                if (data.success && data.data != null) {
                    var curTimestamp = new Date().getTime();
                    var redeemEndTimestamp = new Date();
                    redeemEndTimestamp.setFullYear(new Date(data.data.redeem_end_time).getFullYear());
                    redeemEndTimestamp.setMonth(new Date(data.data.redeem_end_time).getMonth());
                    redeemEndTimestamp.setDate(new Date(data.data.redeem_end_time).getDate());
                    redeemEndTimestamp.setHours(0);
                    redeemEndTimestamp.setMinutes(0);
                    redeemEndTimestamp.setSeconds(0);
                    redeemEndTimestamp.setMilliseconds(0);
                    var resultEndTimestamp = redeemEndTimestamp.getTime() + 24 * 3600 * 1000;
                    if (curTimestamp > resultEndTimestamp) {
                        $scope.isTicketsUser = false;
                    } else {
                        $scope.isTicketsUser = true;
                        $scope.esTicketStart = {
                                year: new Date(data.data.redeem_start_time).getFullYear(),
                                month: new Date(data.data.redeem_start_time).getMonth() + 1,
                                date: new Date(data.data.redeem_start_time).getDate()
                        };
                        $scope.esTicketEnd = {
                                year: new Date(data.data.redeem_end_time).getFullYear(),
                                month: new Date(data.data.redeem_end_time).getMonth() + 1,
                                date: new Date(data.data.redeem_end_time).getDate()
                        };
                    }
                } else if (!data.success) {
                    $scope.isTicketsUser = false;
                }
            }).error(function(data, status) {
                $scope.isTicketsUser = false;
            });

            // 获取符合条件的课程列表
            var dataModel = getViewModel(data.engStudioList,"eng_studio_start_time");

            $scope.viewModel = dataModel;
        };
        $scope.loadViewModel(englishStudioTestingList);
        $scope.$watch("isTicketsUser", function() {});
    });
    angular.bootstrap($("#englishStudioList"), ["englishStudioList"]);

    //tips层-上
    // $(".sh-estudio .mini-btn").on("mouseenter", function() {
    //     var min = englishStudioTestingList.dataConfig.timeOpeningClass;
    //     layer.tips('上课前'+min+'分钟可从此处进入教室', $(this), {
    //         tips: [1, '#313131']
    //     });
    // });
})();
