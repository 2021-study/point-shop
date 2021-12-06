import {notification} from "antd";
import {IconType} from "antd/lib/notification";

export const showNotification = (
    type: IconType, /*'success' | 'info' | 'error' | 'warning'*/
    title: string,
    message: string
) => {
    notification.open({
        type: type,
        message: title,
        description: message,
    })
}
