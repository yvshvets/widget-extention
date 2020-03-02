<template>
  <div></div>
</template>

<script>
import { services } from '../service'

export default {
  name: 'WidgetManager',
  mounted () {
    if (services.auth.isAuth()) {
      services.widget.myWidgets().then((res) => {
        services.widget.widgetContent(res.data).then((d) => {
          var script = document.createElement('script')
          script.src = `/api/widget/${res.data}/content`
          document.head.appendChild(script)
        })
      })
    }
  }
}
</script>
