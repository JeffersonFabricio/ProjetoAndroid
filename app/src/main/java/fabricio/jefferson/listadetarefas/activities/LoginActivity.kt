package fabricio.jefferson.listadetarefas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fabricio.jefferson.listadetarefas.R
import fabricio.jefferson.listadetarefas.database.MyDatabase

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var login: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var dbHelper = MyDatabase(this)

        btnCancelLogin.setOnClickListener {
            finish()
        }

        btnConfirmLogin.setOnClickListener {
            val intent = Intent(this, ListaDeAtividadesActivity::class.java)
            if (initViews()){
                if (validatePassword()){
                    inputValues()
                    if (dbHelper.checkUser(login, password))
                        startActivity(intent)
                    else
                        Toast.makeText(this, "Login or Password invalid!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initViews() : Boolean {

        login = editTxtLoginLogin.text.toString()
        password = editTxtPasswordLogin.text.toString()

        if (
            login.isEmpty() || login.isNullOrBlank()||
            password.isEmpty() || password.isNullOrBlank()
        ){
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun inputValues(){
        intent.putExtra("login", editTxtLoginLogin.text.toString())
        intent.putExtra("password", editTxtPasswordLogin.text.toString())
    }

    private fun validatePassword(): Boolean{

        if (password[0].isUpperCase())
            return true
        else {
            Toast.makeText(this, "Password invalid! start with the first letter uppercase.", Toast.LENGTH_LONG).show()
            return false
        }
    }

}
