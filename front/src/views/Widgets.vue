<template>
  <div class="home">
    <el-button @click="exit">Logout</el-button>
    <el-card v-for="widget in widgets" :key="widget.name">
      {{widget}}
    </el-card>
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
import { logout } from '../components/auth'
import AddButton from '../components/AddButton'
import { allWidget } from '../components/widget'

export default {
  name: 'Home',
  components: {
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
      allWidget()
        .then((response) => {
          this.widgets = response.data
        })
    },
    exit () {
      logout().then(() => {
        this.$router.push('/login')
      })
    },
    handleAddClick () {
      this.addForm.visible = true
    },
    addHandle () {
      this.$refs.upload.submit()
    }
  },
  mounted () {
    this.load()
  }
}
</script>
