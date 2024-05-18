/*
 * Copyright (c) 2023 Mariusz Bernacki <consulting@didalgo.com>
 * SPDX-License-Identifier: Apache-2.0
 */
package com.didalgo.intellij.chatgpt.chat;

import com.didalgo.intellij.chatgpt.text.TextContent;
import com.didalgo.intellij.chatgpt.ui.ToolWindowLocator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;

import java.util.List;
import java.util.concurrent.Future;

public interface ChatLink {

    Key<ChatLink> KEY = Key.create("ChatLink.current");

    static ChatLink forProject(Project project) {
        ToolWindowLocator.ensureActivated(project);
        return project.getUserData(ChatLink.KEY);
    }

    Project getProject();

    InputContext getInputContext();

    ConversationContext getConversationContext();

    Future<?> pushMessage(String prompt, List<? extends TextContent> textContents);

    void addChatMessageListener(ChatMessageListener listener);

    void removeChatMessageListener(ChatMessageListener listener);

    default void regenerateResponse() {

    }
}
