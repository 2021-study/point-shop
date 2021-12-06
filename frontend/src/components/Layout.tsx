import {Layout, Menu, Breadcrumb} from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
} from '@ant-design/icons';
import {useState} from "react";
import 'antd/dist/antd.css';
import FormDemo from "./FormDemo";
import {Token} from "../apis/interfaces";
import {clearLocalStorageByKey} from "../apis/api";
import {showNotification} from "../utils/utils";

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;


type LayoutProps = {
    title: string,
}

const SiderDemo = ({title}:LayoutProps) => {
    const [collapsed, setCollapsed] = useState<boolean>(false)
    const [loginFormToggle, setLoginFormToggle] = useState<boolean>(true)

    const onCollapse = (collapsed : boolean) => {
        console.log(collapsed);
        setCollapsed(collapsed);
    };

    const formRenderer = () => {
        console.log("loginform toggle : ", loginFormToggle)
        if(loginFormToggle) {
            return <FormDemo setLoginFormToggleOff = {setLoginFormToggleOff}/>
        } else if(!loginFormToggle && localStorage.getItem('JWT') != null){
            return <p>로그인 되었습니다.</p>
        }
    }

    const logInOutButtonRenderer = (formToggle : boolean) => {
        console.log("loginout button renderer")
        if(formToggle){
            return (<Menu.Item key="login-menu-item" icon={<UserOutlined />} onClick={login}>
                login
            </Menu.Item>);
        } else {
            return (<Menu.Item key="logout-menu-item" icon={<UserOutlined />} onClick={logOut}>
                logout
            </Menu.Item>);
        }
    }

    const setLoginFormToggleOff = () => {
        setLoginFormToggle(false);
    }

    const setLoginFormToggleOn = () => {
        setLoginFormToggle(true);
    }

    const login = () => {
        if(localStorage.getItem('JWT')!==null) {
            showNotification('warning', "로그인 실패", "아직 로그인 중입니다?");
        } else {
            setLoginFormToggleOn();
        }
    }

    const logOut = () => {
        clearLocalStorageByKey('JWT');
        setLoginFormToggleOn();
    }


    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Sider collapsible collapsed={collapsed} onCollapse={onCollapse}>
                <div className="logo" />
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                    {/*<Menu.Item key="1" icon={<PieChartOutlined />}>*/}
                    {/*    Option 1*/}
                    {/*</Menu.Item>*/}
                    {/*<Menu.Item key="2" icon={<DesktopOutlined />}>*/}
                    {/*    Option 2*/}
                    {/*</Menu.Item>*/}
                    {/*<SubMenu key="sub1" icon={<UserOutlined />} title="User">*/}
                    {/*    <Menu.Item key="3">Tom</Menu.Item>*/}
                    {/*    <Menu.Item key="4">Bill</Menu.Item>*/}
                    {/*    <Menu.Item key="5">Alex</Menu.Item>*/}
                    {/*</SubMenu>*/}
                    {/*<SubMenu key="sub2" icon={<TeamOutlined />} title="Team">*/}
                    {/*    <Menu.Item key="6">Team 1</Menu.Item>*/}
                    {/*    <Menu.Item key="8">Team 2</Menu.Item>*/}
                    {/*</SubMenu>*/}
                    {/*<Menu.Item key="9" icon={<FileOutlined />}>*/}
                    {/*    Files*/}
                    {/*</Menu.Item>*/}
                    {logInOutButtonRenderer(loginFormToggle)}
                </Menu>
            </Sider>
            <Layout className="site-layout">
                <Header className="site-layout-background" style={{ paddingLeft: 10}}><h1>{title}</h1></Header>
                <Content style={{ margin: '0 16px' }}>
                    <Breadcrumb style={{ margin: '16px 0' }}>
                        <Breadcrumb.Item>User</Breadcrumb.Item>
                        <Breadcrumb.Item>Bill</Breadcrumb.Item>
                    </Breadcrumb>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                        {formRenderer()}
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
            </Layout>
        </Layout>
    );
}

export default SiderDemo;
