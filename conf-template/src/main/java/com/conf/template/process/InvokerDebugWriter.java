package com.conf.template.process;

import java.io.IOException;
import java.util.List;

import com.bstek.urule.debug.DebugWriter;
import com.bstek.urule.debug.MessageItem;
import com.conf.common.ToolsUtil;

public class InvokerDebugWriter implements DebugWriter
{
    @Override
    public void write(List<MessageItem> items)
        throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for (MessageItem item : items)
        {
            sb.append(item.toHtml());
        }
        ToolsUtil.invokerLocalSet(sb.toString());
    }
    
}
