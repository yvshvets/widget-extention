<template>
  <div class="home">
    <el-button @click="exit">Logout</el-button>
    <div class="content">
      <widget-info class="widget-element" v-for="widget in widgets" :key="widget.name" :info="widget"
                   @activate="activateHandler" @deActivate="deActivateHandler"/>
    </div>
    <add-button @click="handleAddClick"/>

    <el-dialog :visible.sync="addForm.visible">
      <el-form v-model="addForm">
        <el-upload
          class="upload-demo"
          ref="upload"
          action="/api/widget"
          :auto-upload="false">
          <el-button slot="trigger" size="small" type="primary">open</el-button>
        </el-upload>
      </el-form>
      <el-footer>
        <el-button @click="addHandle">ОК</el-button>
        <el-button @click="addForm.visible = false">Отмена</el-button>
      </el-footer>
    </el-dialog>
  </div>
</template>

<script>
import { services } from '../service'
import AddButton from '../components/AddButton'
import WidgetInfo from '../components/WidgetInfo'

export default {
  name: 'Home',
  components: {
    WidgetInfo,
    AddButton
  },
  data () {
    return {
      widgets: [],
      addForm: {
        visible: false,
        name: '',
        file: ''
      }
    }
  },
  methods: {
    load () {
      services.widget.all()
        .then((response) => {
          this.widgets = response.data
        })
    },
    exit () {
      services.auth.logout().then(() => {
        this.$router.push('/login')
      })
    },
    handleAddClick () {
      this.addForm.visible = true
    },
    addHandle () {
      this.$refs.upload.submit()
    },
    activateHandler (name) {
      services.widget.activate(name).then(() => {
        this.load()
      })
    },
    deActivateHandler (name) {
      services.widget.deActivate(name).then(() => {
        this.load()
      })
    }
  },
  mounted () {
    this.load()
  }
}
</script>

<style lang="scss">
  .content {
    display: flex;
    flex-direction: row;
    align-items: center;
    flex-wrap: wrap;

    .widget-element {
      margin: 10px;
    }
  }
</style>
