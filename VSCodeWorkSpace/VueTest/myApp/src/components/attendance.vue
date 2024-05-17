<script setup>
import { ref, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useRouter } from 'vue-router';
import axios from 'axios';
const router = useRouter();
var myAttendanceData = ref([]);
var myLocation = ref({
  lat: null,
  lng: null,
  province: null,
  city: null,
});
var clockInButtonDisabled = ref(true);
onMounted(() => {
  getMyAttendance();
});

/**
 * 获取我的打卡记录
 */
function getMyAttendance() {
  ElMessage({
    message: '正在获取我的打卡记录',
    type: 'success',
  });
  axios({
    url: "/api/attendance/getMyAttendance",
    method: "get",
    params: {
      "user_id": JSON.parse(localStorage.getItem("user")).user_id
    },
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
        myAttendanceData.value = response.data.data
        if (myAttendanceData.value.length === 0) {
          clockInButtonDisabled.value = false
        }
        var localdate = new Date().toLocaleDateString('zh', { year: 'numeric', month: '2-digit', day: '2-digit' }).replaceAll('/', '-')
        for (let i = 0; i < myAttendanceData.value.length; i++) {
          if (myAttendanceData.value.length > 0 && myAttendanceData.value[i].date.split(" ")[0] === localdate) {
            clockInButtonDisabled.value = true
            break
          }
          else {
            clockInButtonDisabled.value = false
          }
        }
      } else {
        ElMessage.error(response.data.msg)
      }
    })
    .catch(function (error) {
      ElMessage.error("数据请求失败")
      console.log(error);
    });
}

/**
 * 打卡
 */
function clockIn() {
  clockInButtonDisabled.value = true//禁用打卡按钮（打卡流程执行起点）
  ElMessage({
    message: '正在打卡',
    type: 'success',
  });
  getLocation()
}

//获取GPS定位
function getLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(successCallback, showError);
  }
  else {
    ElMessage.error("该浏览器不支持定位。")
  }
}

/**
 * 获取GPS定位成功处理函数
 * @param position
 */
function successCallback(position) {
  var lat = position.coords.latitude
  var lng = position.coords.longitude
  var ggPoint = new BMapGL.Point(lng, lat);//谷歌坐标
  console.log("谷歌坐标", ggPoint)
  convertPoint(ggPoint)//坐标转换
}


/**
 * 定位错误处理
 * @param error 
 */
function showError(error) {
  clockInButtonDisabled.value = false//启用打卡按钮(打卡流程执行终点1)
  console.log("定位错误", error)
  switch (error.code) {
    case error.PERMISSION_DENIED:
      ElMessage.error("用户拒绝对获取地理位置的请求。")
      break;
    case error.POSITION_UNAVAILABLE:
      ElMessageBox.confirm('位置信息不可用。请重新尝试或切换其他浏览器。', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        return
      }).catch(() => {
        return
      })

      break;
    case error.TIMEOUT:
      ElMessage.error("请求用户地理位置超时。")
      break;
    case error.UNKNOWN_ERROR:
      ElMessage.error("未知错误。")
      break;
  }
}


/**
 * 坐标转换
 */
function convertPoint(ggPoint) {
  var convertor = new BMapGL.Convertor();
  var pointArr = [];
  pointArr.push(ggPoint);
  convertor.translate(pointArr,
    COORDINATES_WGS84,
    COORDINATES_BD09,
    (data) => {
      console.log(data);
      if (data.status === 0) {
        var bmPoint = data.points[0]//百度地图坐标
        console.log("百度坐标1", bmPoint)
        myLocation.value.lat = bmPoint.lat
        myLocation.value.lng = bmPoint.lng
        var geoc = new BMapGL.Geocoder();
        geoc.getLocation(bmPoint, function (rs) {//获取地名信息
          var addComp = rs.addressComponents;
          myLocation.value.province = addComp.province
          myLocation.value.city = addComp.city
          console.log("地址信息", myLocation.value)
          /**
           * 上传打卡数据
           */
          uploadClockInData()
        })
      }
      else {
        ElMessage.error("坐标转换失败")
        clockInButtonDisabled.value = false//启用打卡按钮(打卡流程执行终点2)
      }
    })
}

/**
 * 上传打卡数据
 */
function uploadClockInData() {
  var attendance = {
    "userId": JSON.parse(localStorage.getItem("user")).user_id,
    "province": myLocation.value.province,
    "city": myLocation.value.city,
    "lat": myLocation.value.lat,
    "lng": myLocation.value.lng
  }
  axios({
    url: "/api/attendance/clockIn",
    method: "post",
    data: attendance,
    headers: {
      'Content-Type': 'application/json',
      "token": localStorage.getItem("token"),
    }
  })
    .then(function (response) {
      console.log(response);
      if (response.data.success === true) {
        ElMessage({
          message: "打卡成功 位置:" + response.data.msg,
          type: 'success',
        })
        setTimeout(() => {
          router.go(0)
        }, 1000)
      } else {
        ElMessage.error(response.data.msg)
        clockInButtonDisabled.value = false//启用打卡按钮（打卡流程执行终点3）
      }
    })
    .catch(function (error) {
      ElMessage.error("请求失败")
      clockInButtonDisabled.value = false//启用打卡按钮（打卡流程执行终点4）
      console.log(error);
    });
}

/**
 * 结束出差
 */
function endMyBusinessTrip() {
  ElMessageBox.confirm('确定要结束出差吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    axios({
      url: "/api/attendance/endMyBusinessTrip",
      method: "get",
      params: { "user_id": JSON.parse(localStorage.getItem("user")).user_id },
      headers: {
        'Content-Type': 'application/json',
        "token": localStorage.getItem("token"),
      }
    })
      .then(function (response) {
        console.log(response);
        if (response.data.success === true) {
          ElMessage({
            message: "操作成功",
            type: 'success',
          })
        } else {
          ElMessage.error(response.data.msg)
        }
      })
      .catch(function (error) {
        ElMessage.error("请求失败")
        console.log(error);
      });
  }).catch(() => {
    return
  })
}


</script>
<template>
  <div style="height: 100%; overflow:auto;">
    <el-card style="width: 99% ;" shadow="always">
      <div style="display: flex;">
        <el-button type="primary" @click="clockIn" style="position: relative; height:40px; width: 20%;"
          :disabled="clockInButtonDisabled">今日打卡
        </el-button>
        <h2 style="position: relative; left: 20%;">我的打卡记录</h2>
        <el-button type="primary" @click="endMyBusinessTrip"
          style="position: relative; height:40px; width: 20%;left:46%;">结束出差</el-button>
      </div>
    </el-card>
    <el-calendar>
      <template #date-cell="{ data }">
        <p :class="data.isSelected ? 'is-selected' : ''">
          {{ data.day.split('-').slice(1).join('-') }}
          <template v-for="item in myAttendanceData" :key="item.date">
            <el-tag type="success" v-if="item.date.split(' ')[0] == data.day.split('-').slice(0, 3).join('-')">
              已打卡
            </el-tag>
          </template>
        </p>
        <div style="width:100%;" v-for="item in myAttendanceData" :key="item.date">
          <el-tag style="width:100%;" type="success"
            v-if="item.date.split(' ')[0] == data.day.split('-').slice(0, 3).join('-')">
            打卡时间:{{ item.date.split(' ')[1].split(':').slice(0, 2).join(':') }}
          </el-tag>
          <el-tag style="width:100%;" type="success"
            v-if="item.date.split(' ')[0] == data.day.split('-').slice(0, 3).join('-')">
            打卡地点:{{ item.city }}
          </el-tag>
        </div>
      </template>
    </el-calendar>
  </div>
</template>

<style scoped>
.is-selected {
  color: #1989fa;
}
</style>