<template  id="container">
<div id="container">hahahhahah </div>
</template>

<script>
export default {
  name: "MP",
  props: ["startPlace", "endPlace"],
  methods: {
    init() {
      var map = new BMapGL.Map("container");
      // //创建地图解析器实例
      var myGeo = new BMapGL.Geocoder();
      map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
      // console.log("地理坐标:" + this.startPlace, this.endPlace);
      //设置中心点坐标
      myGeo.getPoint(this.startPlace, function (point) {
        if (point) {
          map.centerAndZoom(point, 25); //中心点坐标
        }
      });

      var startLng = 0,
        startLat = 0,
        endLng = 0,
        endLat = 0;
      //设置起点坐标(异步返回)
      myGeo.getPoint(
        this.startPlace,
        function (point) {
          if (point) {
            startLng = point.lng; //经度起始经度
            startLat = point.lat; //纬度起始纬度
          }
        },
        map
      );
      //设置终点坐标(异步返回)
      myGeo.getPoint(
        this.endPlace,
        function (point) {
          if (point) {
            endLng = point.lng;
            endLat = point.lat;
          }
        },
        map
      );
      function createLine() {
        if (startLng !== 0 && startLat !== 0 && endLng !== 0 && endLat != 0) {
          var end = new BMapGL.Point(endLng, endLat); //起始地点
          var start = new BMapGL.Point(startLng, startLat); //目的地
          var driving = new BMapGL.DrivingRoute(map, {
            renderOptions: {
              map: map,
              autoViewport: true,
            },
          });
          driving.search(start, end);
        } else {
          setTimeout(() => {
            createLine();
          }, 100);
        }
      }
      createLine();
    },
  },
  mounted() {
    this.init();
  },
  created() {
    // this.$notify({
    //   title: '提示',
    //   message: '这是一条不会自动关闭的消息',
    //   duration: 0
    // })
  }
};
</script>

<style>
body,
html {
  width: 100%;
  height: 100%;
  margin: 0;
  font-family: "微软雅黑";
}
#container {
  /* margin-left: 25%; */
  width: 100%;
  height: 100%;
}
</style>