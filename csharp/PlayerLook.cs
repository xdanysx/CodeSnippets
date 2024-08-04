using UnityEngine;

public class PlayerLook : MonoBehaviour
{

    // Dieses Skript wird am Spieler-GameObjekt angehangen und dient dazu, dass Der Spieler sich umschauen kann,
    // Natürlich müssen noch die Werte im Inspektor angebeben werden, sowie die Camera

    [Header("Camera")]
    public GameObject playerCamera;
    public Vector3 cameraOffset;
    public float mouseSensitivity;

    private float mouseX;
    private float mouseY;
    private float xRotation = 0f;
    private void Start()
    {
        Cursor.lockState = CursorLockMode.Locked; // Damit die Maus verschwindet
    }

    // Update is called once per frame
    void Update()
    {
        Look();
    }

    void Look()
    {
        playerCamera.transform.position = transform.position + cameraOffset;
        mouseX = Input.GetAxis("Mouse X") * mouseSensitivity * Time.deltaTime;
        mouseY = Input.GetAxis("Mouse Y") * mouseSensitivity * Time.deltaTime;

        xRotation -= mouseY;
        xRotation = Mathf.Clamp(xRotation, -90f, 90f);
        playerCamera.transform.localRotation = Quaternion.Euler(xRotation, 0f, 0f);
        //camera.transform.Rotate(Vector3.up * mouseX);
        transform.Rotate(Vector3.up * mouseX);
        //camera.transform.Rotate(xRotation, mouseX, 0f);
    }

}
