    // 在build.gradle(Module) 中添加以下代码：buildFeatures { viewBinding true }
    // 思考：怎样 读取/修改/删除指定一行
    
class MainActivity : AppCompatActivity() {
    
    private lateinit var ui : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        // 文件参数
        val fileName = "record.txt"
        val file = File(filesDir, fileName) // 内部储存

        /** 写入文件 */
        ui.buttonWrite.setOnClickListener {
            try {
                var content = "0123456789ABCDEF"
                if (!file.exists()) {
                    file.createNewFile()
                }else{
                    content = "追加一行"
                }

                // 整个文件覆盖新内容
                /*
                val fos = FileOutputStream(file)
                fos.write(content.toByteArray())
                //fos.write("\r\n".toByteArray()) //写入换行
                fos.close()
                 */

                // 在文件末尾追加一行
                file.appendBytes(content.toByteArray())
                file.appendBytes("\n".toByteArray())

                if (file.exists()) {
                    AlertDialog.Builder(this).setTitle("提示").setMessage("已经写入 ${filesDir}").setPositiveButton("好", null).create().show()
                }else{
                    AlertDialog.Builder(this).setTitle("提示").setMessage("写入失败").setPositiveButton("好", null).create().show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /** 读取文件 */
        ui.buttonRead.setOnClickListener {
            try {
                if (!file.exists()) {
                    AlertDialog.Builder(this).setTitle("提示").setMessage("文件不存在").setPositiveButton("好", null).create().show()
                }else {
                    val fos = FileInputStream(file)
                    val byte = ByteArray(1024)
                    val len = fos.read(byte)
                    val readContent = String(byte, 0, len)
                    AlertDialog.Builder(this).setTitle("提示").setMessage("文件内容: $readContent").setPositiveButton("好", null).create().show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        /** 删除文件 */
        ui.buttonDelete.setOnClickListener {
            if (!file.exists()) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("文件不存在，无法删除").setPositiveButton("好", null).create().show()
            }else if (file.delete()){
                AlertDialog.Builder(this).setTitle("提示").setMessage("文件成功删除").setPositiveButton("好", null).create().show()
            }
        }
    }
}
