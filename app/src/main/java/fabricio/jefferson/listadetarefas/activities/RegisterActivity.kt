package fabricio.jefferson.listadetarefas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fabricio.jefferson.listadetarefas.R
import fabricio.jefferson.listadetarefas.database.MyDatabase
import fabricio.jefferson.listadetarefas.model.User

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var cpf: String
    private lateinit var email: String
    private lateinit var login: String
    private lateinit var password: String

    var dbHelper = MyDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnCancelRegister.setOnClickListener {
            finish()
        }

        btnConfirmRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            if (initViews()){
                if (validatePassword() && validateEmail()){
                    inputValues()
                    var user = User(0, name, cpf, email, login, password)
                    dbHelper!!.addUser(user)
                    startActivity(intent)
                }
            }
        }
    }

    private fun initViews() : Boolean{

        name = editTxtNameRegister.text.toString()
        cpf = editTxtCpfRegister.text.toString()
        login = editTxtLoginRegister.text.toString()
        password = editTxtPasswordRegister.text.toString()
        email = editTxtEmailRegister.text.toString()

        if (
            name.isEmpty() || name.isNullOrBlank() ||
            cpf.isEmpty() || cpf.isNullOrBlank() ||
            login.isEmpty() || login.isNullOrBlank()||
            password.isEmpty() || password.isNullOrBlank() ||
            email.isEmpty() || email.isNullOrBlank()
        ){
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun inputValues(){
        intent.putExtra("name", editTxtNameRegister.text.toString())
        intent.putExtra("cpf", editTxtCpfRegister.text.toString())
        intent.putExtra("login", editTxtLoginRegister.text.toString())
        intent.putExtra("password", editTxtPasswordRegister.text.toString())
        intent.putExtra("email", editTxtEmailRegister.text.toString())
    }

    private fun validatePassword(): Boolean {
        if (this.password[0].isUpperCase()) {
            return true
        } else {
            Toast.makeText(this, "Password invalid! start with the first letter uppercase.", Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun validateEmail(): Boolean {
        if (email.matches(Patterns.EMAIL_ADDRESS.toRegex()))
            return true
        else {
            Toast.makeText(this, "E-mail invalid!", Toast.LENGTH_LONG).show()
            return false
        }
    }

}
