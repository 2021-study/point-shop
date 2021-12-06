import {Button, Checkbox, Form, Input} from 'antd';
import 'antd/dist/antd.css';
import {loginApi, setLocalStorage, setUpToken} from "../apis/api";
import {showNotification} from "../utils/utils";


type FormDemoProps = {
    setLoginFormToggleOff : ()=>void
}

const FormDemo = ({setLoginFormToggleOff}: FormDemoProps) => {
    const onFinish = async (values: any) => {
        console.log('Success:', values);
        const response = loginApi(values.username, values.password);
        response.then((token)=>{
            setLocalStorage("JWT", token.token!)
            setUpToken(token.token)
            setLoginFormToggleOff();
        }).catch((error)=>{
            console.log("response : ",error )
            console.log("login Res : " , error.response.data);
            showNotification('error', error.response.data.errorCode, error.response.data.errorMessage)
        })


    };

    const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
    };

    return (
        <Form
            name="basic"
            labelCol={{span: 8}}
            wrapperCol={{span: 16}}
            initialValues={{remember: true}}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
        >
            <Form.Item
                label="Username"
                name="username"
                rules={[{required: true, message: 'Please input your username!'}]}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[{required: true, message: 'Please input your password!'}]}
            >
                <Input.Password/>
            </Form.Item>

            <Form.Item name="remember" valuePropName="checked" wrapperCol={{offset: 8, span: 16}}>
                <Checkbox>Remember me</Checkbox>
            </Form.Item>

            <Form.Item wrapperCol={{offset: 8, span: 16}}>
                <Button type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
};

export default FormDemo;
