<script setup>
import { Delete, Edit, Search, Share, Upload, ArrowUp } from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import chartBox from './chartBox.vue';
import { onBeforeMount, onMounted, ref, reactive } from 'vue'
import axios from 'axios';

const staffsearchkey = ref()
var userData = reactive([
  // { name: '小刘', lat: 112, lng: 39, UID: 6110245665 },
  // { name: '王五', lat: 117, lng: 39.9, UID: 7110245665 },
  // { name: '刘备', lat: 113, lng: 37.9, UID: 8110245665 },
  // { name: '曹操', lat: 115, lng: 49.9, UID: 9110245665 },
  // { name: '关羽', lat: 122, lng: 39.9, UID: 10110245665 },
  // { name: '张飞', lat: 126, lng: 39.9, UID: 110245665 },
  // { name: '赵云', lat: 118, lng: 38.9, UID: 120245665 },
  // { name: '诸葛亮', lat: 116, lng: 39, UID: 130245665 },
  // { name: '小王', lat: 116, lng: 39.9, UID: 110245665 },
  // { name: '小李', lat: 115, lng: 38.9, UID: 2110255665 },
  // { name: '张三', lat: 114, lng: 37.9, UID: 311088665 },
  // { name: '李四', lat: 114, lng: 38.9, UID: 4110245665 },
  // { name: '王二', lat: 112, lng: 36.9, UID: 5110245665 },
])
var departmentUser = ref([])
var selectedUser = ref({
  user_id: null,
  user_name: '',
})
var selectedUserName = ref('')
var departmentData = ref([
  { departmentName: '研发部', departmentId: 1 },
  { departmentName: '市场部', departmentId: 2 },
  { departmentName: '采购部', departmentId: 3 },
  { departmentName: '财务部', departmentId: 4 },
  { departmentName: '行政部', departmentId: 5 },
  { departmentName: '项目部', departmentId: 6 },
]);
var selectedDepartment = ref(
  { departmentName: '研发部', departmentId: 1 },
)

onMounted(() => {
  getData()
})

/**
 * 获取数据
 */
function getData() {
  //获取人员数据
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
      if (response.data.success === true && response.data.data != null) {
        userData = response.data.data
        for (var i = 0; i < userData.length; i++) {
          if (userData[i].department_id === selectedDepartment.value.departmentId) {
            departmentUser.value.push(userData[i]);
          }
        }
        selectedUser.value = departmentUser.value[0]
        selectedUserName.value = departmentUser.value[0].user_name
        // 添加覆盖物
      } else {
        ElMessage.error("未能获取到人员数据")
      }
    })
    .catch(function (error) {
      // ElMessage.error("操作失败")
      console.log(error);
    });
}

function SearchStaff() {
  // alert(staffsearchkey.value)
}

/**
 * 选择部门
 * @param item 
 */
function DisplayDepartment(item) {
  selectedDepartment.value = item
  departmentUser.value = []
  for (var i = 0; i < userData.length; i++) {
    if (userData[i].department_id === selectedDepartment.value.departmentId) {
      departmentUser.value.push(userData[i]);
    }
  }
  if (departmentUser.value.length != 0) {
    selectedUser.value = departmentUser.value[0]
    selectedUserName.value = selectedUser.value.user_name
  }
}

/**
 * 选择人员
 * @param item 
 */
function DisplayStaff(item) {
  selectedUser.value = item
  selectedUserName.value = item.user_name
}


const options = [
  {
    value: '采购部',
    label: 'Option1',
  },
  {
    value: '市场部',
    label: 'Option2',
  },
  {
    value: '人事部',
    label: 'Option3',
  },
  {
    value: '技术部',
    label: 'Option4',
  },
  {
    value: '财务部',
    label: 'Option5',
  },
  {
    value: '项目部',
    label: 'Option5',
  },
]
</script>

<template>
  <div class="aside-container">
    <div id="-container">
      <el-input v-model="staffsearchkey" @keydown.enter="SearchStaff" placeholder="搜索人员" :prefix-icon="Search" />
      <h2 style="text-align: center;">所属部门</h2>
      <div id="department-list">
        <el-scrollbar max-height="500px">
          <div class="scrollbar-demo-item" v-for="item in departmentData" :key="item.departmentId"
            v-on:click="DisplayDepartment(item)">
            {{ item.departmentName }}
          </div>
        </el-scrollbar>
      </div>
    </div>
  </div>
  <div class="main-container">
    <div class="upside">
      <div id="staff-list">
        <h2 style="text-align: center;">{{ selectedDepartment.departmentName }}在外人员</h2>
        <el-scrollbar max-height="80%">
          <div class="scrollbar-demo-item" v-for="item in departmentUser" :key="item" v-on:click="DisplayStaff(item)">
            {{ item.user_name }}
          </div>
        </el-scrollbar>
      </div>
      <div class="department-chart">
        <chartBox style="width: 48%;height: 95%;margin-left: 1%;" chartId="outsideStaff-chart1"
          :chartname="selectedDepartment.departmentName + '本月交通方式占比'" :deaprtmentId="selectedDepartment.departmentId"
          :key="selectedDepartment.departmentId">
        </chartBox>
        <chartBox style="width: 48%;height: 95%;margin-left: 1%;" chartId="outsideStaff-chart2"
          :chartname="selectedDepartment.departmentName + '本月支出类型占比'" :deaprtmentId="selectedDepartment.departmentId"
          :key="selectedDepartment.departmentId"></chartBox>
      </div>
    </div>
    <div class="underside">
      <div class="staff-info">
        <img style="margin-top: 30%;height: 80px;width: 80px;" :src="selectedUser == null ? '' : selectedUser.head"
          alt="">
        <h2>{{ selectedUserName }}</h2>
        <h2>出差数据统计</h2>
      </div>
      <div class="staff-chart">
        <chartBox style="width: 32%;height: 95%;margin-left: 1%;" chartId="outsideStaff-chart3"
          :chartname="selectedUser.user_name + '本年出差天数统计'" :userId="selectedUser.user_id" :key="selectedUser.user_id">
        </chartBox>
        <chartBox style="width: 32%;height: 95%;margin-left: 1%;" chartId="outsideStaff-chart4"
          :chartname="selectedUser.user_name + '本年支出类型占比'" :userId="selectedUser.user_id" :key="selectedUser.user_id">
        </chartBox>
        <chartBox style="width: 32%;height: 95%;margin-left: 1%;" chartId="outsideStaff-chart5"
          :chartname="selectedUser.user_name + '本年出差地点统计'" :userId="selectedUser.user_id" :key="selectedUser.user_id">
        </chartBox>
      </div>
    </div>
  </div>
</template>

<style scoped>
#staff-list {
  height: 100%;
  width: 40%;
  border: 1px solid black;
}

.department-chart {
  display: flex;
  flex-direction: row;
  height: 100%;
  width: 60%;
  border: 1px solid black;
}

.upside {
  display: flex;
  flex-direction: row;
  height: 49%;
  width: 100%;
  position: relative;
  /* background-color: bisque; */
}

.staff-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 20%;
  height: 95%;
  border: 1px solid black;
}

.staff-chart {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 80%;
  height: 95%;
  border: 1px solid black;
}

.underside {
  display: flex;
  flex-direction: row;
  align-items: center;
  height: 49%;
  width: 100%;
  position: relative;
  /* background-color: bisque; */
  top: 2%;
}

#department-list {
  width: 100%;
  height: 88%;
  /* background-color: black; */
}

.main-container {
  position: absolute;
  right: 5px;
  /* background-color: aquamarine; */
  width: 75%;
  height: 88%;
  top: 11%;
}

#department-container {
  position: relative;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}

.aside-container {
  position: absolute;
  /* background-color: antiquewhite; */
  width: 24%;
  min-width: 300px;
  height: 88%;
  left: 0;
  top: 11%;
}

.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  cursor: pointer;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}
</style>