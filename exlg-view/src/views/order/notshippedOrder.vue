<!-- 未发货的订单 -->
<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>未发货订单</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" :model="editForm" class="user-search">
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
      <el-table-column align="center" label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >预览</el-button
          >
          <el-button
            @click="upTruck(scope.row)"
            size="mini"
            type="primary"
            style="margin-left: 16px"
          >
            装车
          </el-button>
        </template>
      </el-table-column>
      <!-- 装车弹窗 -->
    </el-table>
    <el-dialog title="选择车辆" :visible.sync="dialogFormVisible" width="500px">
      <el-form>
        <el-form-item label="所有车辆" label-width="120px">
          <el-select v-model="truckMark" placeholder="请选择">
            <el-option
              v-for="truck in trucks"
              :key="truck.id"
              :label="truck.truckMark"
              :value="truck.truckMark"
              :disabled="truck.disabled"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleUpTruck">确 定</el-button>
      </div>
    </el-dialog>
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
import Pagination from "../../components/Pagination";
export default {
  data() {
    return {
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: "预览",
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
      dialogFormVisible: false, //装车弹出控制
      trucks: [],
      truckMark: "",
      selectedOrderId: -1, //选中的订单id
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
    console.log(this.editForm)
  },

  methods: {
    //获取数据  发送axios请求
    getdata() {
      this.loading = true;
      Axios({
        method: "get",
        url: `${baseUrl}/order/notShippedList`,
      }).then((res) => {
        this.loading = false;
        this.AllTableData = res.data.page.list;
        this.pageparm.totalCount = res.data.page.totalCount;
        this.pageparm.totalPage = res.data.page.totalPage;
        //初始化分页信息
        this.setPaginations();
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
        // console.log(parm.pageSize)
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
      this.editFormVisible = true;
      console.log(row)
      this.editForm = row;
    },
    // 关闭编辑、增加弹出框
    closeDialog(formName) {
      this.editFormVisible = false;
      this.$refs[formName].resetFields();
    },
    //给未发货的订单装车
    upTruck(row) {
      this.dialogFormVisible = true; //装车弹出模态框
      //去后台查出所有车辆,并封装是否剩余容量还大于此订单的重量
      this.selectedOrderId = row.id;
      Axios({
        method: "get",
        url: `${baseUrl}/truck/getAllUsableTruck/` + this.selectedOrderId,
      }).then((res) => {
        this.trucks = res.data.trucks;
      });
    },
    //处理确认装车  改变订单状态, 相应车辆的剩余重量扣除
    handleUpTruck() {
      if (this.truckMark == "") {
        //没有选车辆信息
        this.$message({
          type: "error",
          message: "请选择车辆信息",
        });
      } else {
        Axios({
          method: "get",
          url: `${baseUrl}/order/handleUpTruck`,
          params: {
            id: this.selectedOrderId,
            truckMark: this.truckMark
          }
        }).then((res) => {
          if (res.data.code == 0) {
            //发货成功
            // 弹出提示信息
            this.$message({
              type: "success",
              message: res.data.msg,
            });
            //重新加载新的订单数据
            this.getdata();
          } else {
            //弹出提示失败信息
            this.$message({
              type: "error",
              message: res.data.msg,
            });
          }
        });
        //重置初始值
        this.truck = "";
        this.selectedOrderId = -1;
        //关闭模态框
        this.dialogFormVisible = false;
      }
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

 
 

 