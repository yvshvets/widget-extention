<template>
  <div></div>
</template>

<script>
import { services } from '../service'
import { mapMutations } from 'vuex'

export default {
  name: 'WidgetManager',
  mounted () {
    if (services.auth.isAuth()) {
      services.widget.myWidgets().then((res) => {
        res.data.forEach((entry) => {
          services.widget.widgetContent(entry).then((d) => {
            const container = document.createElement('div')
            container.id = entry.replace(/(\W)|(_)/g, '')
            document.body.appendChild(container)
            const script = document.createElement('script')
            script.src = `/api/widget/${entry}/content`
            document.body.appendChild(script)
          })
        })
      })
    }
  },
  methods: {
    ...mapMutations(['addItem'])
  },
  created () {
    window.NTile = {
      services: services,
      route: {
        push: (path) => { this.$router.push(path) }
      }
    }
    this.addItem({ name: 'Главная', path: '/home', action: null })
    this.addItem({ name: 'Виджеты', path: '/widgets', action: null })
  }
}
</script>
