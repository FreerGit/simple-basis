(ns app.window
  (:import
   [org.lwjgl.glfw GLFW GLFWErrorCallback]
   [org.lwjgl.opengl GL GL11]
   [imgui ImGui ImGuiIO]
   [imgui.glfw ImGuiImplGlfw]
   [imgui.gl3 ImGuiImplGl3]))

(defn- init-window [w]
  (when-not (GLFW/glfwInit) (throw (ex-info "Couldn't init glfw" {})))

  (GLFWErrorCallback/createPrint System/err)

  (GLFW/glfwWindowHint (GLFW/GLFW_CONTEXT_VERSION_MAJOR) 3)
  (GLFW/glfwWindowHint (GLFW/GLFW_CONTEXT_VERSION_MINOR) 3)
  (GLFW/glfwWindowHint GLFW/GLFW_OPENGL_PROFILE GLFW/GLFW_OPENGL_CORE_PROFILE)

  (let [window-ptr (GLFW/glfwCreateWindow 600 400 "XXX" 0 0)
        glfw-impl (ImGuiImplGlfw.)
        gl3-impl (ImGuiImplGl3.)]
    (when (= window-ptr 0)
      (GLFW/glfwTerminate)
      (throw (ex-info "Failed to create window" {})))

    (GLFW/glfwMakeContextCurrent window-ptr)
    (GL/createCapabilities)

    (ImGui/createContext)
    (.setIniFilename (ImGui/getIO) nil)

    (.init glfw-impl window-ptr true)
    (.init gl3-impl "#version 330")

    (assoc w
           ::ptr window-ptr
           ::glfw glfw-impl
           ::gl3 gl3-impl)))

(defn create-window []
  (try
    (let [window (init-window {::ptr 0})]
      (println "Window created")
      window)
    (catch Throwable e
      (println "Window creation failed:" (.getMessage e))
      (.printStackTrace e)
      nil)))

(defn run-render-loop [frame-fn window]
  (let [{::keys [ptr glfw gl3]} window]
    (prn ptr glfw gl3)
    (while (not (GLFW/glfwWindowShouldClose ptr))
      (GLFW/glfwPollEvents)

      (GL11/glClear GL11/GL_COLOR_BUFFER_BIT)

      (.newFrame glfw)
      (.newFrame gl3)
      (ImGui/newFrame)

      (frame-fn)

      (ImGui/render)
      (.renderDrawData gl3 (ImGui/getDrawData))

      (GLFW/glfwSwapBuffers ptr))))

(defn cleanup-window [w]
  (let [{:keys [ptr glfw gl3]} w]
    (.dispose gl3)
    (.dispose glfw)
    (ImGui/destroyContext)))
