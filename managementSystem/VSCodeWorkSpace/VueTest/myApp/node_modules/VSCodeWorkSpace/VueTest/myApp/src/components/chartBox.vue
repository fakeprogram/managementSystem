<script setup>
import {
    chart1, homeAttendance,
    homePersonalBusinessTravelTimeRankings,
    geographicalDistributionOfExpatriates,
    proportionOfDepartmentalExpenditureTypes,
    departmentalAnnualExpenditure,
    monthlyExpenditureOfEachDepartment,
    proportionOfTransportationModesInDepartments,
    daysOfPersonalBusinessTripsThisYear,
    proportionOfPersonalExpenditureTypes,
    businessTripCityStatistics,
} from '@/chartRenderer/ChartRender';
import { onMounted, ref, getCurrentInstance } from 'vue';
let internalInstance = getCurrentInstance();
let echarts = internalInstance.appContext.config.globalProperties.$echarts;
const props = defineProps(['chartId', 'chartname', 'chartdata', 'userId', 'deaprtmentId']);
var chartbox = ref(null);
onMounted(() => {
    var data = [
        { name: 'Search Engine', value: 1048 },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
    ]
    switch (props.chartId) {
        case 'home-chart1':
            // console.log(props.chartdata)
            homeAttendance(chartbox.value, props.chartname, props.chartdata)
            break;
        case 'home-chart2':
            homePersonalBusinessTravelTimeRankings(chartbox.value, props.chartname)
            break;

        case 'home-chart3':
            geographicalDistributionOfExpatriates(chartbox.value, props.chartname, props.chartdata)
            break;
        case 'taskOverview-chart1':
            proportionOfDepartmentalExpenditureTypes(chartbox.value, props.chartname, props.deaprtmentId)
            break;
        case 'taskOverview-chart2':
            departmentalAnnualExpenditure(chartbox.value, props.chartname, props.deaprtmentId)
            break;
        case 'taskOverview-chart3':
            monthlyExpenditureOfEachDepartment(chartbox.value, props.chartname, props.chartdata)
            break;
        case 'outsideStaff-chart1':
            proportionOfTransportationModesInDepartments(chartbox.value, props.chartname, props.deaprtmentId)
            break;
        case 'outsideStaff-chart2':
            proportionOfDepartmentalExpenditureTypes(chartbox.value, props.chartname, props.deaprtmentId)
            break;
        case 'outsideStaff-chart3':
            daysOfPersonalBusinessTripsThisYear(chartbox.value, props.chartname, props.userId)
            break;
        case 'outsideStaff-chart4':
            proportionOfPersonalExpenditureTypes(chartbox.value, props.chartname, props.userId)
            break;
        case 'outsideStaff-chart5':
            businessTripCityStatistics(chartbox.value, props.chartname, props.userId)
            break;
        default:
            chart1(chartbox.value, props.chartname, data)
    }


    // if (props.userId) alert(props.userId)
    // chart1(chartbox.value, "testname", data)
    // let Chart = echarts.init(chartbox.value);
    // // 绘制图表
    // let option = {
    //     title: {
    //         text: 'Referer of a Website',
    //         left: 'center',
    //         textStyle: {
    //             color: 'white'
    //         }
    //     },
    //     tooltip: {
    //         trigger: 'item',
    //         confine: true,
    //         renderMode: 'richText',//否则显示会有bug
    //     },
    //     legend: {
    //         orient: 'vertical',
    //         left: 'right',
    //         // right:'right',
    //         top: 'center',
    //         textStyle: {
    //             color: 'white'
    //         }
    //     },
    //     grid: { // 让图表占满容器
    //         top: "0px",
    //         left: "0px",
    //         right: "0px",
    //         bottom: "0px",
    //         containLabel: true,
    //     },
    //     series: [
    //         {
    //             // name: 'Access From',
    //             type: 'pie',
    //             radius: '50%',
    //             data: [
    //                 { value: 1048, name: 'Search Engine' },
    //                 { value: 735, name: 'Direct' },
    //                 { value: 580, name: 'Email' },
    //                 { value: 484, name: 'Union Ads' },
    //                 { value: 300, name: 'Video Ads' }
    //             ],
    //             emphasis: {
    //                 itemStyle: {
    //                     shadowBlur: 10,
    //                     shadowOffsetX: 0,
    //                     shadowColor: 'rgba(0, 0, 0, 0.5)'
    //                 }
    //             }
    //         }
    //     ]
    // };
    // // 渲染图表
    // Chart.setOption(option);
})

</script>

<template>
    <div ref="chartbox" class="chartbox">
    </div>
</template>

<style scoped>
.chartbox {
    position: relative;
    width: 320px;
    height: 230px;
    margin-top: 2px;
    margin-bottom: 2px;
    background-color: rgba(92, 90, 90, 0.797);
}
</style>