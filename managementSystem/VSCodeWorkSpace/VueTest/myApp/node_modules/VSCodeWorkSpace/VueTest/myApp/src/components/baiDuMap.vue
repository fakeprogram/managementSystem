<script setup>
import { onMounted, ref, getCurrentInstance, onBeforeMount } from 'vue';
import chartBox from './chartBox.vue';
import { LockAvatarLay } from '@/customOverlay/customOverlay'
// 通过 internalInstance.appContext.config.globalProperties 获取全局属性或方法
// let internalInstance = getCurrentInstance();
// let echarts = internalInstance.appContext.config.globalProperties.$echarts;
import * as echarts from 'echarts'
import { alertEmits, ElMessage, ElMessageBox } from 'element-plus';
import axios from "axios";
var usersWithAttendance = ref([]);//打卡人员
var allUsers = ref([])//所有人员
var numOfTasks = ref(0);
var chartkey = ref(0);
var map
// const chartTest = ref(null)

onBeforeMount(() => {
    getData()
})

onMounted(() => {
    baiDuMap()
})

/*function getData() {
    //获取数据
    let uData = [
        { name: '小刘', UID: 6110245665 },//lat: 112, lng: 39, 
        { name: '王五', lat: 117, lng: 39.9, UID: 7110245665 },
        { name: '刘备', lat: 113, lng: 37.9, UID: 8110245665 },
        { name: '曹操', lat: 115, lng: 49.9, UID: 9110245665 },
        { name: '关羽', lat: 122, lng: 39.9, UID: 10110245665 },
        { name: '张飞', lat: 126, lng: 39.9, UID: 110245665 },
        { name: '赵云', lat: 118, lng: 38.9, UID: 120245665 },
        { name: '诸葛亮', lat: 116, lng: 39, UID: 130245665 },
        { name: '小王', lat: 116, lng: 39.9, UID: 110245665 },
        { name: '小李', lat: 115, lng: 38.9, UID: 2110255665 },
        { name: '张三', lat: 114, lng: 37.9, UID: 311088665 },
        { name: '李四', lat: 114, lng: 38.9, UID: 4110245665 },
        { name: '王二', lat: 112, lng: 36.9, UID: 5110245665 },
    ]
    return uData
}*/

// echarts
//初始化函数
/*function initEcharts() {
    // 基于准备好的dom，初始化echarts实例
    let Chart = echarts.init(chartTest.value);
    // 绘制图表
    let option = {
        title: {
            text: 'Referer of a Website',
            subtext: 'Fake Data',
            left: 'center',
            textStyle: {
                color: 'white'
            }
        },
        tooltip: {
            trigger: 'item',
            confine: true,
            renderMode: 'richText',//否则显示会有bug
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            // right:'right',
            top: 'center',
            textStyle: {
                color: 'white'
            }
        },
        grid: { // 让图表占满容器
            top: "0px",
            left: "0px",
            right: "0px",
            bottom: "0px",
            containLabel: true,
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: '50%',
                data: [
                    { value: 1048, name: 'Search Engine' },
                    { value: 735, name: 'Direct' },
                    { value: 580, name: 'Email' },
                    { value: 484, name: 'Union Ads' },
                    { value: 300, name: 'Video Ads' }
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    // 渲染图表
    Chart.setOption(option);
}*/

// 渲染人员位置
function userLocation(map, users) {
    let headers = []
    for (let i = 0; i < users.length; i++) {
        if (users[i].lat == null || users[i].lng == null) {
            continue;
        }
        else {
            headers[i] = new LockAvatarLay(new BMapGL.Point(users[i].lng, users[i].lat), users[i].head, {}, users[i].user_id, users[i].user_name, users[i].departmentName, users[i].city);
            map.addOverlay(headers[i]);
            headers[i].addEventListener('click', e => {
                alert("姓名:" + headers[i]._userName + "\n部门:" + headers[i]._departmentName + "\n位置:" + headers[i]._address)
                map.centerAndZoom(new BMapGL.Point(headers[i]._point.lng, headers[i]._point.lat), 15);
            })
            headers[i].show();
            // map.addEventListener('mousemove', e => {
            //     if (e.currentTarget.getZoom() < 7) {
            //         headers[i].hide();
            //     }
            //     else {
            //         headers[i].show();
            //     }
            // })
        }
    }
}

//点击人员列表
function clickUser(e) {
    var user
    chartkey.value = e.user_id
    var a = null
    a = usersWithAttendance.value.find((item) => {
        if (item.user_id == e.user_id) {
            user = item
            map.centerAndZoom(new BMapGL.Point(user.lng, user.lat), 15);
            setTimeout(function () {
                map.setTilt(60)
            }, 800);
            return user;
        }
    })
    if (a == null) alert("该人员没有打卡，无位置信息")
    // map.setZoom(5)
    //如果新中心点在展示范围内，实现平滑移动到新中心点处。否则会直接跳至该点
    // window.setTimeout(function () {
    //     map.centerAndZoom(new BMapGL.Point(e.lat, e.lng), 15);
    //     map.setTilt(60)
    // }, 2000);

    // map.setCenter(new BMapGL.Point(e.lat,e.lng))
}

// 创建地图
function baiDuMap() {
    map = new BMapGL.Map("mapBox")
    var point = new BMapGL.Point(103.9993862008, 30.560570746024);
    map.centerAndZoom(point, 15);
    map.setMapStyleV2({
        styleId: '4b1353c0bb3100bc2f6cc503df5cb32a'
    })

    map.enableScrollWheelZoom(true);

    var opts = {//控件偏移
        offset: new BMapGL.Size(300, 5)
    }
    //var scaleCtrl =new BMapGL.ScaleControl(opts)//比例尺控件
    //map.addControl(scaleCtrl)
    // var zoomCtrl = new BMapGL.ZoomControl(opts)//缩放控件
    // map.addControl(zoomCtrl)

    // var navi3DCtrl = new BMapGL.NavigationControl3D({
    //     offset: new BMapGL.Size(300, 80)
    // });  // 添加3D控件
    // map.addControl(navi3DCtrl);
}

/**
 * 获取数据
 */
function getData() {
    // 获取打卡数据
    axios({
        url: "/api/attendance/getOutSideStaffAttendanceOfToday",
        method: "get",
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                ElMessage({
                    message: response.data.msg,
                    type: 'success',
                })
                usersWithAttendance.value = response.data.data
                // 添加覆盖物
                userLocation(map, usersWithAttendance.value);
            } else {
                ElMessage.error("未能获取到打卡数据")
            }
        })
        .catch(function (error) {
            ElMessage.error("操作失败")
            console.log(error);
        });

    //获取所有人员信息
    axios({
        url: "/api/departmentStatistic/getAllOutSideStaff",
        method: "get",
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                allUsers.value = response.data.data
                // 添加覆盖物
            } else {
                ElMessage.error("未能获取到人员数据")
            }
        })
        .catch(function (error) {
            ElMessage.error("操作失败")
            console.log(error);
        });

    //获取总数任务
    axios({
        url: "/api/departmentStatistic/getNumOfTasks",
        method: "get",
        headers: {
            'Content-Type': 'application/json',
            "token": localStorage.getItem("token"),
        }
    })
        .then(function (response) {
            console.log(response);
            if (response.data.success === true) {
                numOfTasks.value = response.data.data
            } else {
                ElMessage.error("未能获取到任务数据")
            }
        })
        .catch(function (error) {
            ElMessage.error("操作失败")
            console.log(error);
        });
}

/*function foldBox(e) {
    var rollbox = e.currentTarget.parentNode;
    var a = document.getElementById('roll1')
    if (rollbox.style.width == "100%") {
        a.style.width == "300%";
        alert(300)
    }
    else {
        a.style.width == "100px"
        alert(100)
    }
    console.log(8888, rollbox, a.style.width);
}*/

</script>

<template>
    <div class="mapContainer" id="mapBox">
    </div>
    <div class="rightcontainer" id="rightBox">
        <div class="users-box">
            <h2 style="color: white;text-align: center;">出差人员</h2>
            <div class="user-container" v-for="user in allUsers" v-on:click="clickUser(user)">
                <div class="head-img">
                    <img :src="user.head" alt="" style="display: block;width: 100%;height: 100%;border-radius:30px;">
                </div>
                {{ user.user_name }}
            </div>
        </div>
        <div class="attendance">
            <chartBox id="box1" chartId="home-chart1" chartname="今日考勤数据"
                :chartdata="[{ value: usersWithAttendance.length, name: '已打卡' }, { value: allUsers.length - usersWithAttendance.length, name: '未打卡' }]"
                :key="allUsers.length + usersWithAttendance.length">
            </chartBox>
        </div>
    </div>
    <div class="leftcontainer" id="leftBox">
        <div class="roll" id="roll1">
            <!-- <chartBox id="box1" :chartname="'num'" chartdata="888"></chartBox> -->
            <div>
                <h1 style="color: white; margin-top: 30px;">在外人数：{{ allUsers.length }}</h1>
                <h1 style="color: white;margin-top: 30px;">总任务数：{{ numOfTasks }}</h1>
            </div>

            <!-- <chartBox id="box2" :chartname="pie" chartdata="888"></chartBox>
            <chartBox id="box3" :chartname="pie" chartdata="888"></chartBox> -->
            <!-- <div id="select"></div> -->
        </div>
        <div class="roll" id="roll2">
            <chartBox id="box2" chartId="home-chart2" chartname="本年个人出差天数排名">
            </chartBox>
            <!-- <chartBox id="box2" :chartname="pie" chartdata="888"></chartBox>
            <chartBox id="box3" :chartname="pie" chartdata="888"></chartBox> -->
            <!-- <div id="select" v-on:click="foldBox"></div> -->
        </div>
        <div class="roll" id="roll3">
            <chartBox id="box3" chartId="home-chart3" chartname="各地人数分布"
                :chartdata="{ value: allUsers.length - usersWithAttendance.length, name: '无位置信息' }"
                :key="allUsers.length + usersWithAttendance.length">
            </chartBox>
            <!-- <chartBox id="box2" :chartname="pie" chartdata="888"></chartBox>
            <chartBox id="box3" :chartname="pie" chartdata="888"></chartBox>
            <div id="select"></div> -->
        </div>
    </div>
</template>

<!-- 左侧样式 -->
<style scoped>
.leftcontainer .roll {
    position: relative;
    height: 30%;
    /* width: 300%; */
    margin-top: 10px;
    /* border: 1px solid white; */
    box-sizing: border-box;
    display: flex;
    overflow: hidden;
}

.roll div {
    position: relative;
    height: 100%;
    width: 280px;
    max-width: 280px;
    margin-left: 10px;
    background-color: rgba(66, 59, 59, 0.8);
    box-sizing: border-box;
    /* flex-shrink: 0; */
}

#select {
    position: absolute;
    height: 100%;
    right: 0;
    margin: 0;
    width: 20px;
    background-color: aqua;
}
</style>

<!-- 首页 -->
<style scoped>
#mapBox {
    position: relative;
    line-height: 100%;
    display: block;
    text-align: center;
    width: 100%;
    height: 90%;
    background-color: antiquewhite;
}

.BMap_cpyCtrl {
    display: none;
}

.anchorBL {
    display: none;
}

.rightcontainer {
    position: absolute;
    display: block;
    height: 90%;
    width: 20%;
    right: 0;
    top: 10%;
    /* background-color:antiquewhite; */
    /* border: 1px solid white; */
    box-sizing: border-box;
    z-index: 5;
}

.attendance {
    position: relative;
    /* margin-right: 20px; */
    margin-top: 20px;
    /* height: 100%;
    width: 280px; */
    /* margin-left: 10px; */
    background-color: rgba(66, 59, 59, 0.8);
    box-sizing: border-box;
}

.users-box {
    position: relative;
    margin-top: 5px;
    padding-left: 5px;
    padding-right: 5px;
    height: 60%;
    overflow: auto;
    -ms-overflow-style: none;
    display: block;
    border: 1px solid rgb(225, 225, 225);
    border-radius: 20px;
}

.users-box::-webkit-scrollbar {
    display: none;
}

.user-container {
    position: relative;
    margin-bottom: 3px;
    background-color: rgba(49, 49, 61, 0.8);
    height: 55px;
    width: 100%;
    border-radius: 6px;
    display: block;
    color: white;
    text-align: left;
    line-height: 55px;
    padding-left: 60px;
    box-sizing: border-box;
}

.user-container:hover {
    cursor: pointer;
    background-color: rgb(163, 167, 171);
}

.head-img {
    position: absolute;
    top: 7.5%;
    left: 5px;
    display: block;
    height: 85%;
    width: 15%;
    border-radius: 30px;
    border: 0.5px solid rgba(255, 255, 255, 0.8);
    /* background-image: url(''); */
    background-size: contain;
    background-position: center;
    background-repeat: no-repeat;
    box-sizing: content-box;
}

.leftcontainer {
    position: absolute;
    display: block;
    height: 90%;
    width: 20%;
    left: 0;
    top: 10%;
    /* background-color:antiquewhite; */
    /* border: 1px solid rgb(204, 195, 195); */
    box-sizing: border-box;
    z-index: 5;
}

.el-message {
    top: 10px !important;
}
</style>