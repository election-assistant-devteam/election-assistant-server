FROM amazoncorretto:17
RUN yum update -y && \
    amazon-linux-extras install epel -y && \
    yum install -y \
        chromium               \
        # ── Selenium 이 필요로 하는 so 파일들 ──
        libxcb                 \
        libX11-xcb libXcomposite libXdamage libXrandr \
        libXScrnSaver libXi libXcursor libXtst \
        gtk3 pango atk at-spi2-atk \
        nss alsa-lib libdrm libgbm mesa-libgbm \
        freetype fontconfig && \
    yum clean all && rm -rf /var/cache/yum
EXPOSE 8080
COPY ./build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
