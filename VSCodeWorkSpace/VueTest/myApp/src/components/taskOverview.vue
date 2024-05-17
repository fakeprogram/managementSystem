<script setup>
import { onBeforeMount, onMounted, ref, reactive } from 'vue'
import { Delete, Edit, Search, Share, Upload, ArrowUp } from '@element-plus/icons-vue'
import chartBox from './chartBox.vue';
import { ElMessage, ElProgress } from 'element-plus';
import axios from 'axios';
const tasklist = ref();
var taskData = reactive([]);
var selectedTask = ref({
    task: {
        addressId: 3,
        address_name: "深圳市南山区科技园南二路1016号",
        city: "深圳市",
        county: "南山区",
        departmentId: 6,
        departmentName: "项目部",
        endTime: "2023-03-14 18:00:00",
        last_detail: "1016号",
        province: "广东省",
        startTime: "2023-03-12 09:00:00",
        street: "科技园南二路",
        taskDetail: "开发",
        taskFile: "开发代码.zip",
        taskId: 3,
        taskName: "",
        taskProgress: 70,
        taskStatus: 2,
        taskType: "开发",
    },
    receivers: []
});
var departmentTask = ref([])
var departmentData = ref([]);
var selectedDepartment = ref({
    departmentId: null,
    value: '',
    label: '',
})
var selectedDepartmentName = ref('')
var monthlyExpenditureOfEachDepartment = ref([])
var tasksearchkey = ref('');
var map

onMounted(() => {
    baiDuMap()
    getData()
})

// 创建地图
function baiDuMap() {
    map = new BMapGL.Map("map-container")
    var point = new BMapGL.Point(103.404, 35.915);
    map.centerAndZoom(point, 5);
    map.setMapStyleV2({
        styleId: '4b1353c0bb3100bc2f6cc503df5cb32a'
    })
    map.enableScrollWheelZoom(true);
    // map.setHeading(30);
    // map.setTilt(75);
    var opts = {//控件偏移
        offset: new BMapGL.Size(300, 5)
    }

}

//切换部门
function Change(e) {
    selectedDepartment.value = e
    departmentTask.value = []//要先清空否则会有Bug
    var length = taskData.length;
    for (var i = 0; i < length; i++) {
        if (taskData[i].task.departmentId === selectedDepartment.value.departmentId) {
            var task = taskData[i]
            departmentTask.value.push(task)
        }
    }
    selectedTask.value = departmentTask.value[0]
}
//搜索任务
function SearchTask() {
    // alert(tasksearchkey.value)
}

const options = [
    {
        value: 'Option1',
        label: 'Option1',
    },
    {
        value: 'Option2',
        label: 'Option2',
    },
    {
        value: 'Option3',
        label: 'Option3',
    },
    {
        value: 'Option4',
        label: 'Option4',
    },
    {
        value: 'Option5',
        label: 'Option5',
    },
]

//task-detail
// function TaskDetail() {
//     var height = tasklist.value.clientHeight
//     var left = tasklist.value.clientWidth
//     var taskdetail = document.getElementById('task-detail')
//     taskdetail.style.height = height + 'px'
//     taskdetail.style.left = left + 10 + 'px'
// }

//显示任务详情
function DisplayDetail(e) {
    selectedTask.value = e;
    var taskdetail = document.getElementById('task-detail')
    taskdetail.style.width = "400px"
}

//关闭任务详情
function CloseDetail() {
    var taskdetail = document.getElementById('task-detail')
    taskdetail.style.width = "0px"
}

//显示
function DisplayTaskData(e) {
    var taskdata = document.getElementById('task-data')
    if (e.target.dataset.isclose === 'false') {
        taskdata.style.height = "0px"
        e.target.dataset.isclose = 'true'
    }
    else {
        taskdata.style.height = "600px"
        e.target.dataset.isclose = 'false'
    }
    console.log(e)
}

/**
 * 获取数据
 */
function getData() {
    //获取任务数据
    axios({
        url: "/api/taskManagement/getOnGoingTask",
        method: "post",
        params: {
            "user_id": JSON.parse(localStorage.getItem("user")).user_id
        },
        headers: {
            'Content-Type': 'application/json',
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJfaWQiOjQsImV4cCI6MTcxNDgwOTI2MH0.qF9PLDg3wm8AwepwQskVu3SYooYFicqpWTedWX2mBBE"
        }
    })
        .then(function (response) {
            if (response.data.success == true) {
                console.log(response.data.data);
                taskData = response.data.data
                console.log(taskData);
                departmentTask.value = taskData
                genarateDepartmentData()
                /**
                 * 地址解析
                 */
                for (let i = 0; i < taskData.length; i++) {
                    adds.push(taskData[i].task.address_name)
                    labels.push(taskData[i].task.taskName)
                }
                bdGEO()
            }
            else {
                ElMessage.error("获取数据失败")
            }
        })
        .catch(function (error) {
            console.log(error);
        });

    /**
     * 获取各部门本月支出数据
     */
    axios({
        url: "/api/departmentStatistic/monthlyExpenditureOfEachDepartment",
        method: "get",
        headers: {
            'Content-Type': 'application/json',
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsInVzZXJfaWQiOjQsImV4cCI6MTcxNDgwOTI2MH0.qF9PLDg3wm8AwepwQskVu3SYooYFicqpWTedWX2mBBE"
        }
    })
        .then(function (response) {
            if (response.data.success == true) {
                console.log(response.data.data);
                monthlyExpenditureOfEachDepartment.value = response.data.data
                console.log(monthlyExpenditureOfEachDepartment);
            }
            else {
                ElMessage.error("获取数据失败")
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * 生成部门数据
 */
function genarateDepartmentData() {
    for (let i = 0; i < taskData.length; i++) {
        let departmentId = taskData[i].task.departmentId
        let findDepartment = departmentData.value.find(item => item.departmentId == departmentId)
        if (findDepartment == undefined) {
            departmentData.value.push({
                departmentId: departmentId,
                value: taskData[i].task.departmentName,
                label: taskData[i].task.departmentName,
            })
        }
    }
    selectedDepartment.value = departmentData.value[0]
}

/**
 * 处理接收者数组
 * @param receivers 接收者数组
 */
function processReceivers(receivers) {
    if (receivers.length == 0) return ""
    var receivers_str = "|  "
    for (var i = 0; i < receivers.length; i++) {
        receivers_str += receivers[i].user_name + "  |  "
    }
    return receivers_str
}

/**
 * 部门选择修改处理
 */
function clearSelect(e) {
    if (e == '' || e == null) {
        departmentTask.value = taskData;
        selectedTask.value = taskData[0]
    }
}

/**
 * 地址解析
 */
var myGeo = new BMapGL.Geocoder();
var adds = [];
var labels = []
var index = 0;
function bdGEO() {
    if (index < adds.length) {
        var add = adds[index];
        var label = labels[index];
        geocodeSearch(add, label);
        index++;
    }
}
function geocodeSearch(add, label) {
    if (index < adds.length) {
        setTimeout(bdGEO, 400);
    }
    myGeo.getPoint(add, function (point) {
        if (point) {
            var address = new BMapGL.Point(point.lng, point.lat);
            addMarker(address, new BMapGL.Label(label, { offset: new BMapGL.Size(10, -10) }));
        }
    }, "");
}
// 编写自定义函数,创建标注
function addMarker(point, label) {
    var marker = new BMapGL.Marker(point);
    map.addOverlay(marker);
    marker.setLabel(label);
}
</script>

<template>
    <div id="map-container">
    </div>
    <div ref="tasklist" class="task">
        <div class="search-box">
            <el-input v-model="tasksearchkey" @keydown.enter="SearchTask" placeholder="搜索任务" :prefix-icon="Search" />
        </div>
        <div class="filter-box">
            <el-select v-model="selectedDepartmentName" @change="clearSelect" clearable placeholder="选择部门">
                <el-option v-for="item in departmentData" :key="item.value" :label="item.label" :value="item.value"
                    @click="Change(item)" />
            </el-select>
        </div>
        <h2 style="text-align: center;">正在进行的任务</h2>
        <el-scrollbar max-height="500px">
            <div style="cursor: pointer;overflow: hidden;" v-for="item in departmentTask" :key="item.task.taskId"
                class="scrollbar-demo-item" v-on:click="DisplayDetail(item)">
                {{ item.task.taskName }}
            </div>
        </el-scrollbar>
        <div id="task-detail">
            <el-button type="primary" @click="CloseDetail"
                style="position:sticky;top:1px; right: 0px;width: 100%;z-index:10;">关闭</el-button>
            <div style="padding:0">
                <el-form label-width="auto" label-position="top" class="demo-ruleForm" style="font-size: x-small;"
                    status-icon>
                    <p style="font-size:15px;color: rgba(0, 0, 0, 0.7);margin: 5px 0 5px 0">任务进度:
                        <el-progress :text-inside="true" :stroke-width="24" :percentage="selectedTask.task.taskProgress"
                            status="success" />
                    </p>
                    <el-form-item label="任务名称">
                        <el-input type="textarea" :placeholder="selectedTask.task.taskName" disabled />
                    </el-form-item>
                    <el-form-item label="任务详细信息">
                        <el-input type="textarea" :placeholder="selectedTask.task.taskDetail" disabled />
                    </el-form-item>
                    <el-form-item label="部门">
                        <el-input :placeholder="selectedTask.task.departmentName" disabled />
                    </el-form-item>
                    <el-form-item label="任务地址">
                        <el-input type="textarea" :placeholder="selectedTask.task.address_name" disabled />
                    </el-form-item>
                    <el-form-item label="开始时间">
                        <el-date-picker type="datetime" :placeholder="selectedTask.task.startTime"
                            value-format="YYYY-MM-DD HH:mm:ss" disabled />
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-date-picker type="datetime" :placeholder="selectedTask.task.endTime"
                            value-format="YYYY-MM-DD HH:mm:ss" disabled />
                    </el-form-item>

                    <el-form-item label="负责人员">
                        <el-input :placeholder="processReceivers(selectedTask.receivers)" disabled />
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>

    <div id="task-data-container">
        <!-- <el-button type="button" style="position: relative; width: 100%;height: 20px;" data-isclose='false'
            v-on:click="DisplayTaskData">
            <el-icon>
                <ArrowUp />
            </el-icon>
        </el-button>
        <div id="task-data">
            <chartBox class="task-data-chart"></chartBox>
            <chartBox class="task-data-chart"></chartBox>
            <chartBox class="task-data-chart"></chartBox>
        </div> -->
    </div>

    <div id="department-data-container">
        <chartBox class="department-data-chart" chartId="taskOverview-chart1"
            :chartname="selectedDepartment.value + '本月支出类型占比'" :deaprtmentId="selectedDepartment.departmentId"
            :key="selectedDepartment.departmentId">
        </chartBox>
        <chartBox class="department-data-chart" chartId="taskOverview-chart2"
            :chartname="selectedDepartment.value + '本年支出统计'" :deaprtmentId="selectedDepartment.departmentId"
            :key="selectedDepartment.departmentId">
        </chartBox>
        <chartBox class="department-data-chart" chartId="taskOverview-chart3" chartname="本月各部门支出统计"
            :chartdata="monthlyExpenditureOfEachDepartment" :key="monthlyExpenditureOfEachDepartment.length">
        </chartBox>
    </div>
</template>

<style scoped>
#map-container {
    z-index: 0;
    position: absolute;
    width: 100%;
    height: 100%;
    /* background-color: blueviolet; */
}

.department-data-chart {
    width: 95%;
    height: 33%;
    /* background-color: brown; */
}

#department-data-container {
    position: absolute;
    top: 90px;
    right: 0px;
    width: 350px;
    height: 600px;
    min-width: 0;
    display: flex;
    flex-direction: column;
    align-items: center;
    /* background-color: aquamarine; */
}

#task-data-container {
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: auto;
    width: 350px;
    bottom: 0;
    right: 30%;
    background-color: bisque;
}

#task-data {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 600px;
    width: 350px;
    transition: height 0.5s;
    overflow: hidden;
    background-color: bisque;
}

.scrollbar-demo-item {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    margin: 10px;
    text-align: center;
    border-radius: 4px;
    background: var(--el-color-primary-light-9);
    color: var(--el-color-primary);
}

#task-detail {
    position: absolute;
    left: 100%;
    top: 0;
    width: 0px;
    height: 100%;
    background-color: white;
    overflow-x: hidden;
    overflow-y: auto;
    -ms-overflow-style: none;
    /* IE and Edge */
    scrollbar-width: none;
    /* Firefox */
    transition: width 0.3s;
}

#task-detail::-webkit-scrollbar {
    display: none;
}

.task {
    position: relative;
    top: 20px;
    left: 10px;
    width: 25%;
    height: 85%;
    background-color: white;
}

.router-link-active {
    text-decoration: none;
    color: rgb(38, 38, 41);
}

a {
    text-decoration: none;
    color: black;
}
</style>