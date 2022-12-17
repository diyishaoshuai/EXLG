<!--已经发货的订单-->
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>已发货订单</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="editForm" class="user-search">
      <el-form-item>
        <el-select size="small" v-model="type" placeholder="请选择订单状态">
          <el-option
            v-for="type in this.types"
            :label="type.key"
            :value="type.value"
            :key="type.value"
            @click.native="selectStatus(type.key, type.value)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input
          size="small"
          placeholder="请输入要查询的信息"
          v-model="searchKey"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          size="small"
          type="primary"
          icon="el-icon-search"
          @click="search"
          >搜索</el-button
        >
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table
      size="small"
      :data="currTableData"
      highlight-current-row
      v-loading="loading"
      border
      element-loading-text="拼命加载中"
      style="width: 100%"
    >
      <el-table-column align="center" type="index" width="60" prop="id">
      </el-table-column>
      <el-table-column
        sortable
        prop="orderId"
        label="订单号"
        width="150"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        sortable
        prop="startPlace"
        label="起始地"
        width="200"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        sortable
        prop="endPlace"
        label="目的地"
        width="200"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        sortable
        prop="status"
        label="订单状态"
        width="120"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        sortable
        prop="createTime"
        label="创建时间"
        width="200"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        sortable
        prop="totalPrice"
        label="订单总额"
        width="200"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column align="left" label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button
            @click="openMap(scope.row)"
            size="mini"
            type="primary"
            style="margin-left: 16px"
          >
            查看物流
          </el-button>
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >预览</el-button
          >
          <el-button v-show="scope.row.status === '已送达'" size="mini" @click="sginOrder(scope.row)"
            >签收</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-bind:child-msg="pageparm"
      @callFather="callFather"
    ></Pagination>
    <!-- 预览界面 -->
    <el-dialog
      :title="title"
      :visible.sync="editFormVisible"
      width="50%"
      @click="closeDialog('editForm')"
    >
      <el-form label-width="120px" :model="editForm" ref="editForm">
        <el-row>
          <el-col :span="12">
            <el-form-item label="订单号">
              <el-input
                size="small"
                v-model="editForm.orderId"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="寄件人姓名">
              <el-input
                size="small"
                v-model="editForm.sendName"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="寄件人电话">
              <el-input
                size="small"
                v-model="editForm.sendUserTel"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="寄件地址">
              <el-input
                size="small"
                v-model="editForm.startPlace"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="运费">
              <el-input
                size="small"
                v-model="editForm.totalPrice"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="发货站点">
              <el-input
                size="small"
                v-model="editForm.sendStage"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="收货人姓名">
              <el-input
                size="small"
                v-model="editForm.receiveName"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="收货人电话">
              <el-input
                size="small"
                v-model="editForm.receiveUserTel"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="收获地址">
              <el-input
                size="small"
                v-model="editForm.endPlace"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="订单总重量(kg)">
              <el-input
                size="small"
                v-model="editForm.totalWeigh"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="订单状态">
              <el-input
                size="small"
                v-model="editForm.status"
                auto-complete="off"
                disabled
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Axios from "axios";
const baseUrl = "http://localhost:8888";
import orderDetailPage from "../orderDetailPage.vue";
import Pagination from "../../components/Pagination";
export default {
  components: { orderDetailPage },
  data() {
    return {
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: "预览",
      types: [
        { key: "全部", value: -1 },
        { key: "已发货", value: 1 },
        { key: "运输中", value: 2 },
        { key: "已送达", value: 3 },
        { key: "已签收", value: 4 },
      ],
      type: "", //选中的订单状态
      searchKey: "", //搜索的关键字
      editForm: {
        id: "",
        orderId: "", // 订单号
        sendName: "", //寄件人姓名
        sendUserTel: "", //寄件人电话
        startPlace: "", //寄件地址
        totalPrice: "", // 总共运费
        sendStage: "", //发货站点
        receiveName: "", //收货人姓名
        receiveUserTel: "", //收货人电话
        endPlace: "", //收获地址
        totalWeigh: "", //总重量
        status: "", //订单状态
        createTime: "", //创建时间
        updateTime: "", //更新时间
      },
      AllTableData: [], //所有的数据
      currTableData: [], //当前的分页数据
      // 分页参数
      pageparm: {
        currPage: 1,
        pageSize: 10,
        totalCount: 10,
        totalPage: 0,
      },
    };
  },
  // 注册组件
  components: {
    Pagination,
  },

  /**
   * 创建完毕
   */
  created() {
    this.getdata();
  },

  /**
   * 里面的方法只有被调用才会执行
   */
  methods: {
    sginOrder(row){
      //签收订单
      Axios({
        method: 'get',
        url: `${baseUrl}/order/signOrder/`+row.id
      }).then(res => {
        if(res.data.code === 0){
          //签收成功,重新加载数据
          this.$message.success('签收成功');
          this.getdata()
        }else{
          this.$message.error(res.data.msg);
        }
      })
    },
    //打开地图
    openMap(row) {
      this.$router.push({
        path: "/order/shippedOrder/orderDetailPage",
        query: { startPlace: row.startPlace, endPlace: row.endPlace },
      });
    },
    //获取数据  发送axios请求
    getdata() {
      this.loading = true;
      Axios({
        method: "get",
        url: `${baseUrl}/order/list`,
        contentType: "application/json;charset=UTF-8",
      }).then((res) => {
        this.loading = false;
        this.AllTableData = res.data.page.list;
        this.pageparm.totalCount = res.data.page.totalCount;
        this.pageparm.totalPage = res.data.page.totalPage;
        //初始化分页信息
        this.setPaginations();
      });
    },
    //查询指定的订单状态
    selectStatus(key, status) {
      this.type = key;
      if (status === -1) {
        //查询全部
        this.getdata();
      }
      Axios({
        method: "get",
        url: `${baseUrl}/order/getOrderByStatus`,
        params: {
          status: status,
        },
      }).then((res) => {
        this.loading = false;
        //订单数据赋值
        this.$nextTick(() => {
          this.AllTableData = res.data.page.list;
          this.pageparm.totalCount = res.data.page.totalCount;
          this.pageparm.totalPage = res.data.page.totalPage;
          this.setPaginations();
        });
      });
    },
    //初始化分页
    setPaginations() {
      this.pageparm.currPage = 1; //默认显示第一页
      this.pageparm.pageSize = 10; //默认大小10
      //显示数据
      this.currTableData = this.AllTableData.filter((item, index) => {
        return index < this.pageparm.pageSize;
      });
    },
    // 分页插件事件
    callFather(parm) {
      if (parm.pageSize) {
        //处理页面大小
        this.pageparm.currPage = 1;
        this.pageparm.pageSize = parm.pageSize;
        this.currTableData = this.AllTableData.filter((item, index) => {
          return index < this.pageparm.pageSize;
        });
      }
      if (parm.currentPage) {
        //处理页码改变
        //获取当前页的第一个元素的索引
        let index = this.pageparm.pageSize * (parm.currentPage - 1);
        let currAllData = this.pageparm.pageSize * parm.currentPage; //当前所有数据总数
        let tablist = [];
        // let allData = this.AllTableData.list; //所有数据
        for (let i = index; i < currAllData; i++) {
          if (this.AllTableData[i]) {
            tablist.push(this.AllTableData[i]);
          }
        }
        this.currTableData = tablist;
      }
    },
    // 搜索事件
    search() {
      Axios({
        method: "get",
        url: `${baseUrl}/order/getOrderByKey`,
        params: {
          key: this.searchKey,
        },
      }).then((res) => {
        this.loading = false;
        this.$nextTick(() => {
          this.AllTableData = res.data.page.list;
          this.pageparm.totalCount = res.data.page.totalCount;
          this.pageparm.totalPage = res.data.page.totalPage;
          this.setPaginations();
        });
      });
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      console.log(row);
      this.editFormVisible = true;
      this.editForm = row;
    },
    // 关闭编辑、增加弹出框
    closeDialog(formName) {
      this.editFormVisible = false;
      this.$refs[formName].resetFields();
    },
  },
  watch: {
    //监视搜索关键字,一旦关键字发送改变,就去数据库中查询相应的信息
    searchKey(newValue, oldValue) {
      //去数据库查询订单号、起始地、目的地与此关键字相关的信息
      Axios({
        method: "get",
        url: `${baseUrl}/order/getOrderByKey`,
        params: {
          key: newValue,
        },
      }).then((res) => {
        this.loading = false;
        this.$nextTick(() => {
          this.AllTableData = res.data.page.list;
          this.pageparm.totalCount = res.data.page.totalCount;
          this.pageparm.totalPage = res.data.page.totalPage;
          this.setPaginations();
        });
      });
    },
  },
};
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>

 
 

 