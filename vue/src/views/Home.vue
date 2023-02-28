<template>
  <div>
<!--    <h1 style="text-align: center; margin-bottom: 20px">后台管理系统主页</h1>-->
    <el-row>
      <el-col :span="24">
        <div id="title1"  style="width: 80%; height: 60px;margin:auto">
        </div>
      </el-col>管理系统
    </el-row>


    <el-row :gutter="10" style="margin-bottom: 60px"> <!--gutter 每块之间的间隔-->
      <el-col :span="6">  <!--span 感觉是把 24 中取 6 出来，也就是分成了4份空间-->
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" style="margin-right: 10px"></i>用户总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
<!--            <el-tag type="primary" style="font-size: large">100</el-tag>-->
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-s-marketing" style="margin-right: 10px"></i>销售总量</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            1000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-s-finance" style="margin-right: 10px"></i>交易额</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            1000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div><i class="el-icon-s-shop" style="margin-right: 10px"></i>门店总数</div>
          <div style="padding: 10px 0; text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>

      <el-col :span="12">
        <el-card id="main" class="box-card" shadow="always" style="width: 90%; height: 400px;margin:auto">
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card id="pie" class="box-card" shadow="always" style="width: 90%; height: 400px;margin:auto">
        </el-card>
      </el-col>
    </el-row>

  </div>

</template>

<script>
//导入Echarts
import * as echarts from 'echarts';


export default {
  name: "Home",
  data() {
    return {

    }
  },
  mounted() { //页面元素渲染之后触发（和 created() 相反）

    //后台管理系统标题动画
    var titleChartDom = document.getElementById('title1');
    var titleChart = echarts.init(titleChartDom);
    var titleOption = {
      graphic: {
        elements: [
          {
            type: 'text',
            left: 'center',
            top: 'center',
            style: {
              text: 'TZHousing ARTMS',  //泰州市房管局登记注册交易管理系统 Taizhou City Housing Authority Registration Transaction Management System
              fontSize: 50,
              fontWeight: 'bold',
              lineDash: [0, 200],
              lineDashOffset: 0,
              fill: 'transparent',
              stroke: '#0077ff',
              lineWidth: 2
            },
            keyframeAnimation: {
              duration: 3000,
              loop: false,
              keyframes: [
                {
                  percent: 0.7,
                  style: {
                    fill: 'transparent',
                    lineDashOffset: 200,
                    lineDash: [200, 0]
                  }
                },
                {
                  // Stop for a while.
                  percent: 0.8,
                  style: {
                    fill: 'transparent'
                  }
                },
                {
                  percent: 1,
                  style: {
                    fill: 'white'
                  }
                }
              ]
            }
          }
        ]
      }
    };


    //折线柱状图
    const chartDom = document.getElementById('main');
    const myChart = echarts.init(chartDom);
    const option = {
      title: {
        text: '各季度用户注册数量统计',
        subtext: '趋势图',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: ["第一季度","第二季度","第三季度","第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line',
          smooth: true
        },
        {
          data: [],
          type: 'bar',
        }
      ]
    };

    //饼图
    var pieChartDom = document.getElementById('pie');
    var myPieChart = echarts.init(pieChartDom);
    var pieOption = {
      title: {
        text: '各季度用户注册数量统计图',
        subtext: '数量对比',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '60%',
          label: { //显示百分比
            normal: {
              show: true,
              position: 'inside',
              formatter: '{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比

              textStyle: {
                align: 'center',
                baseline: 'middle',
                fontFamily: '微软雅黑',
                fontSize: 13,
                fontWeight: 'normal',//也可以是数字400
                color:'#fff',
              }
            }
          },
/*          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],*/

          data: [], //填这个，格式看上面注释
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

    this.request.get("/echarts/members").then(res =>{
      //填空
      //option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data
      //数据准备完毕后渲染
      myChart.setOption(option)

      pieOption.series[0].data = [
        {name:"第一季度", value: res.data[0]},
        {name:"第二季度", value: res.data[1]},
        {name:"第三季度", value: res.data[2]},
        {name:"第四季度", value: res.data[3]},
      ]
      myPieChart.setOption(pieOption)
    })


    //后台管理系统标题动画
    titleOption && titleChart.setOption(titleOption);
    //折线柱状图
    option && myChart.setOption(option);
    //饼图
    pieOption && myPieChart.setOption(pieOption);
  }
}
</script>

<style scoped>

</style>